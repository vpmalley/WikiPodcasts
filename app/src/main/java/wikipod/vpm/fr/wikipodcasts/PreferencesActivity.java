package wikipod.vpm.fr.wikipodcasts;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

/**
 * Created by vincent on 05/10/14.
 */
public class PreferencesActivity extends PreferenceActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onBuildHeaders(List<Header> target) {
    loadHeadersFromResource(R.xml.preferenceheaders, target);
  }

  @Override
  protected boolean isValidFragment(String fragmentName) {
    // any preference screen should be implemented as defined in the headers section of doc.
    if ("wikipod.vpm.fr.wikipodcasts.PreferenceCategoryFragment".equals(fragmentName)) {
      return true;
    }
    return false;
  }
}
