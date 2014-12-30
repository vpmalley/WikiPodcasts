package fr.vpm.wikipod.location;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.vpm.wikipod.Constants;

/**
 * Provides unprecise location.
 *
 * Created by vince on 28/11/14.
 */
public class AndroidLocationProvider implements LocationProvider, LocationListener {

  private final Context context;

  private final LocalisationListener listener;

  public AndroidLocationProvider(Context context, LocalisationListener listener) {
    this.context = context;
    this.listener = listener;
  }

  @Override
  public Status acquireCurrentLocation() {
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
        locMan.requestLocationUpdates(3, 2, locCriteria, this, Looper.getMainLooper());
        status = Status.LOCATION_IN_PROGRESS;
      } else {
        status = Status.NO_PROVIDER;
      }
    } else {
      listener.onLocalisationChanged(getLocalisationFromLocation(lastLocation));
      status = Status.LOCATION_AVAILABLE;
    }
    return status;
  }

  /**
   * Once the {@link android.location.LocationListener} used to receive location has received the location, it must notify this provider with a call to this method.
   *
   */
  public void locationSatisfying() {
    LocationManager locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    locMan.removeUpdates(this);
  }

  @Override
  public void onLocationChanged(Location location) {
    locationSatisfying();
    Localisation loc = getLocalisationFromLocation(location);
    listener.onLocalisationChanged(loc);
  }

  private Localisation getLocalisationFromLocation(Location location) {
    Localisation localisation = new Localisation(location);
    if (Geocoder.isPresent()) {
      List<Address> addresses = new ArrayList<>();
      try {
        addresses.addAll(new Geocoder(context).getFromLocation(location.getLatitude(), location.getLongitude(), Localisation.DEFAULT_MAX_RESULTS));
      } catch (IOException e) {
        Log.w("location", e.toString());
      }
      localisation.setNearbyAddresses(addresses);
    }
    return localisation;
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {

  }

  @Override
  public void onProviderDisabled(String provider) {

  }
}
