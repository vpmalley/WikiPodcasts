package wikipod.vpm.fr.wikipodcasts.search;

/**
 * When an address is picked (for example in an AddressPickerFragment), this listener is triggered.
 *
 * Created by vince on 29/12/14.
 */
public interface AddressPickedListener {

  /**
   * Triggers a listener once an address has been picked
   * @param position the position in the list of the picked address
   * @param requestCode the request code associated with this pick
   */
  void onAddressPicked(int position, int requestCode);
}
