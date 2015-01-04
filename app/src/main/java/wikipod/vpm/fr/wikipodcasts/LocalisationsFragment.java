package wikipod.vpm.fr.wikipodcasts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.vpm.wikipod.db.DatabaseHelper;
import fr.vpm.wikipod.location.Localisation;
import fr.vpm.wikipod.location.LocalisationListener;
import fr.vpm.wikipod.location.LocationProvider;
import wikipod.vpm.fr.wikipodcasts.search.LocalisationSearcher;
import wikipod.vpm.fr.wikipodcasts.util.ProgressBarListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class LocalisationsFragment extends Fragment implements LocalisationListener {

  /**
   * The fragment argument representing the section number for this
   * fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";

  private AbsListView locationsView;

  private ProgressBarListener progressListener;

  private ArrayAdapter<Localisation> locationsAdapter;

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static LocalisationsFragment newInstance(int sectionNumber) {
    LocalisationsFragment fragment = new LocalisationsFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }

  public LocalisationsFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_locations, container, false);
    ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.processing);
    progressListener = new ProgressBarListener(progressBar);
    locationsView = (AbsListView) rootView.findViewById(R.id.locations);

    final EditText searchField = (EditText) rootView.findViewById(R.id.searchField);

    filterLocations(searchField);

    // manage search button clicks
    ImageButton searchButton = (ImageButton) rootView.findViewById(R.id.searchButton);
    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String searchText = searchField.getText().toString();
        searchField.setText("");
        new LocalisationSearcher(getActivity(), LocalisationsFragment.this, progressListener).searchLocalisationByName(searchText);
      }
    });

    // manage locate button clicks
    ImageButton locateButton = (ImageButton) rootView.findViewById(R.id.locateButton);
    locateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LocationProvider.Status status = new LocalisationSearcher(getActivity(), LocalisationsFragment.this, progressListener).searchLocalisation();
        Toast.makeText(getActivity(), "tried acquiring location, resulted in " + status.name(), Toast.LENGTH_SHORT).show();
      }
    });

    // manage locations clicks
    locationsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // opening the fragment showing the articles for this location
        Fragment fragment = ArticlesFragment.newInstance(locationsAdapter.getItem(position));
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
      }
    });

    locationsAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, new ArrayList<Localisation>());
    locationsView.setAdapter(locationsAdapter);

    fillLocationsFromDB();

    return rootView;
  }

  private DatabaseHelper getDatabaseHelper() {
    return ((FramingActivity) getActivity()).getHelper();
  }

  private void fillLocationsFromDB() {
    DatabaseHelper dbHelper = getDatabaseHelper();
    Dao<Localisation, Long> localisationDao = null;
    try {
      localisationDao = dbHelper.getDao(Localisation.class);
    } catch (SQLException e) {
      Log.w("db", "cannot retrieve locations. " + e.toString());
    }
    if (localisationDao != null) {
      try {
        locationsAdapter.addAll(localisationDao.queryForAll());
      } catch (SQLException e) {
        Log.w("db", "cannot retrieve locations. " + e.toString());
      }
    }
    locationsAdapter.notifyDataSetChanged();
  }

  /**
   * Adds filtering from search field over the locations in the list
   * @param searchField the text field for searching locations
   */
  private void filterLocations(EditText searchField) {searchField.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence searchText, int start, int before, int count) {
      if (locationsAdapter != null) {
        locationsAdapter.getFilter().filter(searchText.toString());
      }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
  });
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    ((FramingActivity) activity).onSectionAttached(
            getArguments().getInt(ARG_SECTION_NUMBER));
  }

  @Override
  public void onLocalisationChanged(Localisation localisation) {
    locationsAdapter.add(localisation);
    locationsAdapter.notifyDataSetChanged();
    Dao<Localisation, Long> localisationDao;
    try {
      localisationDao = getDatabaseHelper().getDao(Localisation.class);
      localisationDao.create(localisation);
    } catch (SQLException e) {
      Log.w("db", "Cannot persist the localisation. " + e.toString());
    }
  }
}
