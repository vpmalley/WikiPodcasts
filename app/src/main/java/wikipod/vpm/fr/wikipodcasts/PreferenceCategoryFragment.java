package wikipod.vpm.fr.wikipodcasts;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by vincent on 05/10/14.
 */
public class PreferenceCategoryFragment extends PreferenceFragment {

  public static final String CATEGORY = "category";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if ("wiki".equals(getArguments().getString(CATEGORY))) {
      addPreferencesFromResource(R.xml.wikipreferences);
    }
  }

}
