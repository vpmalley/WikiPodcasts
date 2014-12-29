package wikipod.vpm.fr.wikipodcasts.search;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.vpm.wikipod.location.Localisation;
import fr.vpm.wikipod.location.LocalisationListener;
import wikipod.vpm.fr.wikipodcasts.R;
import wikipod.vpm.fr.wikipodcasts.util.ProgressBarListener;

/**
 * Created by vince on 28/12/14.
 */
public class LocalisationSearcher implements LocalisationListener {

  private final Context context;

  private final AbsListView locationsView;

  private final ProgressBarListener progressListener;

  public LocalisationSearcher(Context context, AbsListView locationsView, ProgressBarListener progressListener) {
    this.context = context;
    this.locationsView = locationsView;
    this.progressListener = progressListener;
  }

  @Override
  public void onLocationChanged(Localisation localisation) {
    String geocodingInfo = "geocoding implem found";
    if (!Geocoder.isPresent()){
      geocodingInfo = "no geocoding implem";
    }
    Toast.makeText(context, "received localisation " + localisation.getLatitude() + ", " +
            localisation.getLongitude() + ", " + geocodingInfo, Toast.LENGTH_SHORT).show();
    progressListener.stopRefreshProgress();
    List<Address> addresses = localisation.getNearbyAddresses();
    List<String> printableAddresses = getPrintableAddresses(addresses);

    locationsView.setAdapter(new ArrayAdapter<String>(context, R.layout.list_item, printableAddresses));
  }

  public Localisation searchLocation(String searchName) {
    List<Address> addresses = new ArrayList<>();
    try {
      if (Geocoder.isPresent()) {
        addresses = new Geocoder(context).getFromLocationName(searchName, Localisation.DEFAULT_MAX_RESULTS);
      }
    } catch (IOException e) {
      Log.w("location", e.toString());
    }
    progressListener.stopRefreshProgress();
    List<String> printableAddresses = getPrintableAddresses(addresses);
    locationsView.setAdapter(new ArrayAdapter<String>(context, R.layout.list_item, printableAddresses));
    return new Localisation(addresses);
  }

  private List<String> getPrintableAddresses(List<Address> addresses) {
    List<String> printableAddresses = new ArrayList<>();
    for (Address address : addresses) {
      StringBuilder addressLines = new StringBuilder();
      int i = 0;
      while (i < address.getMaxAddressLineIndex()){
        addressLines.append(address.getAddressLine(i++));
      }
      printableAddresses.add(addressLines.toString());
    }
    return printableAddresses;
  }

}
