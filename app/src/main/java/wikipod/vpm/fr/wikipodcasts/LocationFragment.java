package wikipod.vpm.fr.wikipodcasts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.vpm.wikipod.location.Localisation;
import fr.vpm.wikipod.location.LocalisationListener;
import fr.vpm.wikipod.location.LocationProvider;
import wikipod.vpm.fr.wikipodcasts.search.LocalisationSearcher;
import wikipod.vpm.fr.wikipodcasts.util.ProgressBarListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class LocationFragment extends Fragment implements LocalisationListener {

  /**
   * The fragment argument representing the section number for this
   * fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";

  private AbsListView locationsView;

  private ProgressBarListener progressListener;

  private List<Localisation> localisations = new ArrayList<>();

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static LocationFragment newInstance(int sectionNumber) {
    LocationFragment fragment = new LocationFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }

  public LocationFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_locations, container, false);
    ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.processing);
    progressListener = new ProgressBarListener(progressBar);
    locationsView = (AbsListView) rootView.findViewById(R.id.locations);
    final EditText searchField = (EditText) rootView.findViewById(R.id.searchField);

    ImageButton searchButton = (ImageButton) rootView.findViewById(R.id.searchButton);
    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new LocalisationSearcher(getActivity(), LocationFragment.this, progressListener).searchLocalisationByName(searchField.getText().toString());
      }
    });

    ImageButton locateButton = (ImageButton) rootView.findViewById(R.id.locateButton);
    locateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LocationProvider.Status status = new LocalisationSearcher(getActivity(), LocationFragment.this, progressListener).searchLocalisation();
        Toast.makeText(getActivity(), "tried acquiring location, resulted in " + status.name(), Toast.LENGTH_SHORT).show();
      }
    });
    return rootView;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    ((FramingActivity) activity).onSectionAttached(
            getArguments().getInt(ARG_SECTION_NUMBER));
  }

  @Override
  public void onLocalisationChanged(Localisation localisation) {
    localisations.add(localisation);
    locationsView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.list_item, localisations));
  }
}
