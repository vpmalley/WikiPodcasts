package wikipod.vpm.fr.wikipodcasts;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import fr.vpm.wikipod.wiki.Article;
import wikipod.vpm.fr.wikipodcasts.bean.LocalArticles;
import wikipod.vpm.fr.wikipodcasts.search.ArticleSearcher;
import wikipod.vpm.fr.wikipodcasts.util.ArticlePager;
import wikipod.vpm.fr.wikipodcasts.util.ProgressBarListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticlesFragment extends Fragment {
  /**
   * The fragment argument representing a geographical location
   */
  private static final String ARG_LOCATION = "section_number";

  private AbsListView articlesView;

  private ProgressBarListener progressListener;
  private Location location;

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static ArticlesFragment newInstance(Location location) {
    ArticlesFragment fragment = new ArticlesFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARG_LOCATION, location);
    fragment.setArguments(args);
    return fragment;
  }

  public ArticlesFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_articles, container, false);
    ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.processing);
    progressListener = new ProgressBarListener(progressBar);
    articlesView = (AbsListView) rootView.findViewById(R.id.articles);

    articlesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Article clickedArticle = (Article) parent.getAdapter().getItem(position);
        Intent i = new Intent(getActivity(), ArticleActivity.class);

        ArrayList<Article> clickedArticleAsList = new ArrayList<Article>();
        clickedArticleAsList.add(clickedArticle);

        i.putExtra(ArticlePager.ARTICLES_KEY, new LocalArticles(clickedArticleAsList));
        i.putExtra(ArticlePager.INITIAL_POS_KEY, 0);
        startActivity(i);
      }
    });

    location = getArguments().getParcelable(ARG_LOCATION);
    if (location != null) {
      new ArticleSearcher(getActivity(), articlesView, progressListener).searchAroundLocation(location);
    }
    return rootView;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    ((FramingActivity) activity).onSectionAttached(
            getArguments().getInt(ARG_LOCATION));
  }
}
