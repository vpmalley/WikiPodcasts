package fr.vpm.wikipod.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;

import java.util.List;

import fr.vpm.wikipod.Constants;

/**
 * Provides unprecise location.
 *
 * Created by vince on 28/11/14.
 */
public class AndroidLocationProvider implements LocationProvider {

  private final Context context;

  public AndroidLocationProvider(Context context) {
    this.context = context;
  }

  @Override
  public Status acquireCurrentLocation(LocationListener listener) {
    Status status;
    Criteria locCriteria = new Criteria();
    locCriteria.setAccuracy(Criteria.ACCURACY_LOW);
    locCriteria.setSpeedRequired(false);
    locCriteria.setAltitudeRequired(false);
    locCriteria.setCostAllowed(false);

    LocationManager locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    Location lastLocation = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if ((lastLocation == null) || (lastLocation.getTime() < System.currentTimeMillis() - Constants.LOCATION_EXPIRY)) {
      List<String> availableProviders = locMan.getProviders(locCriteria, true);
      if ((availableProviders != null) && (!availableProviders.isEmpty())) {
        locMan.requestLocationUpdates(3, 2, locCriteria, listener, Looper.getMainLooper());
        status = Status.LOCATION_IN_PROGRESS;
      } else {
        status = Status.NO_PROVIDER;
      }
    } else {
      listener.onLocationChanged(lastLocation);
      status = Status.LOCATION_AVAILABLE;
    }
    return status;
  }

  @Override
  public void locationSatisfying(LocationListener listener) {
    LocationManager locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    locMan.removeUpdates(listener);
  }
}
