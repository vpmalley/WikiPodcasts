package wikipod.vpm.fr.wikipodcasts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import fr.vpm.wikipod.location.AndroidLocationProvider;
import fr.vpm.wikipod.location.LocationProvider;
import fr.vpm.wikipod.wiki.Article;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticlesFragment extends Fragment {
  /**
   * The fragment argument representing the section number for this
   * fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";

  private AbsListView articlesView;

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static ArticlesFragment newInstance(int sectionNumber) {
    ArticlesFragment fragment = new ArticlesFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }

  public ArticlesFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_articles, container, false);
    Button actionButton = (Button) rootView.findViewById(R.id.action);
    articlesView = (AbsListView) rootView.findViewById(R.id.articles);

    articlesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Article clickedArticle = (Article) parent.getAdapter().getItem(position);
        Toast.makeText(getActivity(), clickedArticle.getTitle(), Toast.LENGTH_SHORT).show();
      }
    });

    actionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LocationProvider locP = new AndroidLocationProvider(getActivity());
        LocationProvider.Status status = locP.acquireCurrentLocation(new ArticleSearcher(getActivity(), articlesView));
        Toast.makeText(getActivity(), "tried acquiring location, resulted in " + status.name(), Toast.LENGTH_SHORT).show();
      }
    });
    return rootView;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    ((ArticlesActivity) activity).onSectionAttached(
            getArguments().getInt(ARG_SECTION_NUMBER));
  }
}
