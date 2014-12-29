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
import wikipod.vpm.fr.wikipodcasts.AddressPickerFragment;
import wikipod.vpm.fr.wikipodcasts.R;
import wikipod.vpm.fr.wikipodcasts.util.ProgressBarListener;

/**
 * Created by vince on 28/12/14.
 */
public class LocalisationSearcher implements LocalisationListener, AddressPickedListener {

  private static final int ADDRESS_REQ = 1001;

  private final Context context;

  private final AbsListView locationsView;

  private final ProgressBarListener progressListener;
  private Localisation currentLocalisation;

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


    if (localisation.getNearbyAddresses().size() == 1){
      localisation.setPickedAddress(localisation.getNearbyAddresses().get(0));
      currentLocalisation = localisation;
      onAddressPicked(0, ADDRESS_REQ);
    } else {
      List<Address> addresses = localisation.getNearbyAddresses();
      ArrayList<String> printableAddresses = getPrintableAddresses(addresses);
      new AddressPickerFragment().openAddressPicker(null, printableAddresses, this, ADDRESS_REQ);
    }
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
    Localisation result = new Localisation(addresses, null);
    if (addresses.size() == 1){
      currentLocalisation = new Localisation(addresses, addresses.get(0));
      onAddressPicked(0, ADDRESS_REQ);
    } else {
      ArrayList<String> printableAddresses = getPrintableAddresses(addresses);
      // open a dialog fragment
      new AddressPickerFragment().openAddressPicker(null, printableAddresses, this, ADDRESS_REQ);
    }
    return result;
  }

  private ArrayList<String> getPrintableAddresses(List<Address> addresses) {
    ArrayList<String> printableAddresses = new ArrayList<>();
    for (Address address : addresses) {
      printableAddresses.add(Localisation.getAddressLines(address));
    }
    return printableAddresses;
  }

  @Override
  public void onAddressPicked(int position, int requestCode) {
     if (ADDRESS_REQ == requestCode) {
       currentLocalisation.setPickedAddress(currentLocalisation.getNearbyAddresses().get(position));
       List<Localisation> localisations = new ArrayList<>();
       localisations.add(currentLocalisation);
       locationsView.setAdapter(new ArrayAdapter<Localisation>(context, R.layout.list_item, localisations));
     }
  }
}
