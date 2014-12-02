package fr.vpm.wikipod.location;

import android.location.LocationListener;

/**
 * Created by vince on 29/11/14.
 */
public interface LocationProvider {

  public enum Status {
    LOCATION_IN_PROGRESS,
    LOCATION_AVAILABLE,
    NO_PROVIDER
  }

  /**
   * Figures available providers, and requires location from the best. The location will be delivered to the past {@link android.location.LocationListener}.
   * Returns a status on the situation
   *
   * @param listener the implementation of {@link android.location.LocationListener} that will receive and use the location.
   * @return a {@link fr.vpm.wikipod.location.AndroidLocationProvider.Status} on the situation: either the location is required, there is no provider
   * (i.e. GPS and network are disabled) or the location has recently been acquired (and the listener will receive it).
   */
  public Status acquireCurrentLocation(LocationListener listener);

  /**
   * Once the {@link android.location.LocationListener} used to receive location has received the location, it must notify this provider with a call to this method.
   *
   */
  public void locationSatisfying(LocationListener listener);
}
