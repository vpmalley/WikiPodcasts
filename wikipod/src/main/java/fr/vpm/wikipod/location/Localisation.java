package fr.vpm.wikipod.location;

import android.location.Address;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 21/12/14.
 */
public class Localisation extends Location{

  public static final int DEFAULT_MAX_RESULTS = 10;
  private List<Address> nearbyAddresses = new ArrayList<>();

  private Address pickedAddress;

  public Localisation(Location l) {
    super(l);
  }

  public Localisation(List<Address> nearbyAddresses) {
    super("Geocoder");
    this.nearbyAddresses = nearbyAddresses;
  }

  public void setNearbyAddresses(List<Address> nearbyAddresses) {
    this.nearbyAddresses = nearbyAddresses;
  }

  public List<Address> getNearbyAddresses() {
    return nearbyAddresses;
  }

  public void setPickedAddress(Address pickedAddress) {
    this.pickedAddress = pickedAddress;
  }

  public Address getPickedAddress() {
    return pickedAddress;
  }

  public static String getAddressLines(Address address) {
    StringBuilder addressLines = new StringBuilder();
    int i = 0;
    while (i < address.getMaxAddressLineIndex()){
      addressLines.append(address.getAddressLine(i++));
    }
    return addressLines.toString();
  }


  @Override
  public String toString() {
    String display;
    if (pickedAddress != null) {
      display = getAddressLines(pickedAddress);
    } else {
      display = "Lat " + getLatitude() + ", Lon " + getLongitude();
    }
    return display;
  }
}
