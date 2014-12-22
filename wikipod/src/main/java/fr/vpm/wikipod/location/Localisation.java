package fr.vpm.wikipod.location;

import android.location.Address;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 21/12/14.
 */
public class Localisation extends Location{

  private List<Address> nearbyAddresses = new ArrayList<>();

  private Address pickedAddress;

  public Localisation(Location l) {
    super(l);
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
}
