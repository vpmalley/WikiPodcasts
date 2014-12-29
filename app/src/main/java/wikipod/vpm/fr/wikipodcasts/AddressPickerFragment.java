package wikipod.vpm.fr.wikipodcasts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import wikipod.vpm.fr.wikipodcasts.search.AddressPickedListener;

/**
 * This is a dialog to pick an address among others. Two arguments must be passed to this Fragment:
 * {@link wikipod.vpm.fr.wikipodcasts.AddressPickerFragment#ARGS_ADDR} should link to a list of String, one for each address
 * {@link wikipod.vpm.fr.wikipodcasts.AddressPickerFragment#REQUEST_CODE} should be an integer used for callback on the activity
 * (the activity will be called on its {@link AddressPickedListener#onAddressPicked(int, int)} ).
 *
 * Created by vince on 29/12/14.
 */
public class AddressPickerFragment extends DialogFragment {

  public static final String ARGS_ADDR = "args_addresses";

  public static final String REQUEST_CODE = "request_code";

  private int requestCode;

  private AddressPickedListener listener;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    requestCode = getArguments().getInt(REQUEST_CODE);

    ArrayList<String> allAddresses = getArguments().getStringArrayList(ARGS_ADDR);

    ArrayAdapter<String> blogsAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, allAddresses);

    return new AlertDialog.Builder(getActivity())
            .setTitle(R.string.pick_address)
            .setAdapter(blogsAdapter, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int position) {
                listener.onAddressPicked(position, requestCode);
              }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing - just close the dialog
              }
            })
            .create();
  }

  /**
   * Opens a dialog to pick an address among a list
   * @param activity used to obtain the fragment manager
   * @param addresses the list of pickable addresses
   * @param listener will be called back with the picked position in the address list and the passed request code
   * @param requestCode the identifier of the request, used when calling back the listener
   */
  public void openAddressPicker(Activity activity, ArrayList<String> addresses, AddressPickedListener listener, int requestCode){
    this.listener = listener;
    Bundle args = new Bundle();
    args.putInt(AddressPickerFragment.REQUEST_CODE, requestCode);
    args.putStringArrayList(AddressPickerFragment.ARGS_ADDR, addresses);
    setArguments(args);
    show(activity.getFragmentManager(), "addressPicker");
  }

}
