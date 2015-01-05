package wikipod.vpm.fr.wikipodcasts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import fr.vpm.wikipod.db.DatabaseHelper;


/**
 * This is the main activity, mostly a frame around the fragment containing the actual data and behaviour
 */
public class FramingActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

  /**
   * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
   */
  private NavigationDrawerFragment mNavigationDrawerFragment;

  /**
   * Used to store the last screen title. For use in {@link #restoreActionBar()}.
   */
  private CharSequence mTitle;

  private DatabaseHelper databaseHelper = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_articles);

    mNavigationDrawerFragment = (NavigationDrawerFragment)
            getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    mTitle = getTitle();

    // Set up the drawer.
    mNavigationDrawerFragment.setUp(
            R.id.navigation_drawer,
            (DrawerLayout) findViewById(R.id.drawer_layout));
  }

  @Override
  public void onNavigationDrawerItemSelected(int position) {
    Fragment fragment = null;
    if (0 == position) {
      fragment = LocalisationsFragment.newInstance(position + 1);
      FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction()
              .replace(R.id.container, fragment)
              .commit();
    }
  }

  public void onSectionAttached(int number) {
    switch (number) {
      case 1:
        mTitle = getString(R.string.title_section_locations);
        break;
      case 2:
        mTitle = getString(R.string.title_section2);
        break;
      case 3:
        mTitle = getString(R.string.title_section3);
        break;
    }
  }

  public void restoreActionBar() {
    ActionBar actionBar = getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.setTitle(mTitle);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (!mNavigationDrawerFragment.isDrawerOpen()) {
      // Only show items in the action bar relevant to this screen
      // if the drawer is not showing. Otherwise, let the drawer
      // decide what to show in the action bar.
      getMenuInflater().inflate(R.menu.articles, menu);
      restoreActionBar();
      return true;
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    Intent i = null;

    if (id == R.id.action_settings) {
      i = new Intent(FramingActivity.this, PreferencesActivity.class);
      startActivity(i);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public DatabaseHelper getHelper() {
    if (databaseHelper == null) {
      databaseHelper =
              OpenHelperManager.getHelper(this, DatabaseHelper.class);
    }
    return databaseHelper;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (databaseHelper != null) {
      OpenHelperManager.releaseHelper();
      databaseHelper = null;
    }
  }
}

