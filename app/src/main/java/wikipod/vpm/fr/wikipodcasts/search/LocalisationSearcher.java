package wikipod.vpm.fr.wikipodcasts.search;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.vpm.wikipod.location.AndroidLocationProvider;
import fr.vpm.wikipod.location.Localisation;
import fr.vpm.wikipod.location.LocalisationListener;
import fr.vpm.wikipod.location.LocationProvider;
import wikipod.vpm.fr.wikipodcasts.AddressPickerFragment;
import wikipod.vpm.fr.wikipodcasts.util.ProgressBarListener;

/**
 * Created by vince on 28/12/14.
 */
public class LocalisationSearcher implements LocalisationListener, AddressPickedListener {

  private static final int ADDRESS_REQ = 1001;

  private final Activity activity;

  private final LocalisationListener localisationListener;

  private final ProgressBarListener progressListener;

  private List<Address> nearbyAddresses = new ArrayList<>();

  private Localisation currentLocalisation = null;

  public LocalisationSearcher(Activity activity, LocalisationListener localisationListener, ProgressBarListener progressListener) {
    this.activity = activity;
    this.localisationListener = localisationListener;
    this.progressListener = progressListener;
  }

  /**
   * Determines the localisation based on the location of the device (first entry-point)
   * @return a status telling whether location can be obtained
   */
  public LocationProvider.Status searchLocalisation() {
    progressListener.startRefreshProgress();
    LocationProvider locP = new AndroidLocationProvider(activity, this);
    return locP.acquireCurrentLocation();
  }

  /**
   * Determines the localisation based on the name of the place (second entry-point)
   * @param searchName the name of the targetted place
   */
  public void searchLocalisationByName(String searchName) {
    progressListener.startRefreshProgress();
    nearbyAddresses = new ArrayList<>();
    try {
      if (Geocoder.isPresent()) {
        nearbyAddresses = new Geocoder(activity).getFromLocationName(searchName, Localisation.DEFAULT_MAX_RESULTS);
      }
    } catch (IOException e) {
      Log.w("location", e.toString());
    }

    if (nearbyAddresses.isEmpty()){
      Toast.makeText(activity, "No place found with that name", Toast.LENGTH_SHORT).show();
    } else if (nearbyAddresses.size() == 1){
      onAddressPicked(0, ADDRESS_REQ);
    } else {
      ArrayList<String> printableAddresses = getPrintableAddresses(nearbyAddresses);
      // open a dialog fragment
      new AddressPickerFragment().openAddressPicker(activity, printableAddresses, this, ADDRESS_REQ);
    }
    progressListener.stopRefreshProgress();
  }

  /**
   * When location is obtained, that is where the location is received and transformed
   * @param localisation the localisation received from the LocationProvider
   */
  @Override
  public void onLocalisationChanged(Localisation localisation) {
    String geocodingInfo = "geocoding implem found";
    if (!Geocoder.isPresent()){
      geocodingInfo = "no geocoding implem";
    }
    Toast.makeText(activity, "received localisation " + localisation.getLatitude() + ", " +
            localisation.getLongitude() + ", " + geocodingInfo, Toast.LENGTH_SHORT).show();

    currentLocalisation = localisation;
    if (localisation.getNearbyAddresses().isEmpty()){
      localisationListener.onLocalisationChanged(localisation);
    } else if (localisation.getNearbyAddresses().size() == 1){
      onAddressPicked(0, ADDRESS_REQ);
    } else {
      nearbyAddresses = localisation.getNearbyAddresses();
      ArrayList<String> printableAddresses = getPrintableAddresses(nearbyAddresses);
      new AddressPickerFragment().openAddressPicker(activity, printableAddresses, this, ADDRESS_REQ);
    }
    progressListener.stopRefreshProgress();
  }

  /**
   * Transforms a list of addresses to a list of strings for display purposes. The order stays the same
   * @param addresses the list of addresses to transform
   * @return a list of displayable String, with the same length and in the same order as the list of Address
   */
  private ArrayList<String> getPrintableAddresses(List<Address> addresses) {
    ArrayList<String> printableAddresses = new ArrayList<>();
    for (Address address : addresses) {
      printableAddresses.add(Localisation.getAddressLines(address));
    }
    return printableAddresses;
  }

  /**
   * Once an address is picked for the localisation, this is triggered
   * @param position the position in the list of the picked address
   * @param requestCode the request code associated with this pick
   */
  @Override
  public void onAddressPicked(int position, int requestCode) {
     if (ADDRESS_REQ == requestCode) {
       if (currentLocalisation == null) {
         currentLocalisation = new Localisation(nearbyAddresses.get(position));
       } else {
         currentLocalisation.setNearbyAddress(nearbyAddresses.get(position));
       }
       localisationListener.onLocalisationChanged(currentLocalisation);
     }
  }
}
