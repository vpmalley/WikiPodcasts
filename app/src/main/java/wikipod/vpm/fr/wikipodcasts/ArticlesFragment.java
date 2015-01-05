package wikipod.vpm.fr.wikipodcasts;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.vpm.wikipod.db.DatabaseHelper;
import fr.vpm.wikipod.location.Localisation;
import fr.vpm.wikipod.wiki.Article;
import fr.vpm.wikipod.wiki.ArticleListener;
import wikipod.vpm.fr.wikipodcasts.search.ArticleSearcher;
import wikipod.vpm.fr.wikipodcasts.util.ArticlePager;
import wikipod.vpm.fr.wikipodcasts.util.ProgressBarListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticlesFragment extends Fragment implements ArticleListener {
  /**
   * The fragment argument representing a geographical location
   */
  private static final String ARG_LOCATION = "section_number";

  private AbsListView articlesView;

  private ProgressBarListener progressListener;

  private Localisation location;

  private ArrayAdapter<Article> articlesAdapter;

  private ArrayList<Article> articles = new ArrayList<>();

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static ArticlesFragment newInstance(Localisation localisation) {
    ArticlesFragment fragment = new ArticlesFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARG_LOCATION, localisation);
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
        Intent i = new Intent(getActivity(), ArticleActivity.class);

        i.putExtra(ArticlePager.ARTICLES_KEY, articles);
        i.putExtra(ArticlePager.INITIAL_POS_KEY, position);
        startActivity(i);
      }
    });

    articlesAdapter = new ArrayAdapter<Article>(getActivity(), R.layout.list_item, this.articles);
    articlesView.setAdapter(articlesAdapter);

    location = getArguments().getParcelable(ARG_LOCATION);
    fillArticles();
    return rootView;
  }

  /**
   * Fills the view with articles corresponding to the location, if it is set.
   */
  private void fillArticles() {
    if (location != null) {
      articles = location.getArticles(getDatabaseHelper());
      if (!articles.isEmpty()) {
        articlesAdapter.clear();
        articlesAdapter.addAll(articles);
      } else {
        new ArticleSearcher(getActivity(), this, progressListener).searchAroundLocation(location);
      }
    }
  }

  private DatabaseHelper getDatabaseHelper() {
    return ((FramingActivity) getActivity()).getHelper();
  }

  @Override
  public void onArticlesFound(ArrayList<Article> articles) {
    this.articles = articles;
    articlesAdapter.clear();
    articlesAdapter.addAll(this.articles);
    try {
      Dao<Article, Long> articleDao = getDatabaseHelper().getDao(Article.class);
      for (Article article : this.articles) {
        article.setLocalisation(location);
        articleDao.create(article);
      }
    } catch (SQLException e) {
      Log.w("db", "Cannot create articles. " + e.toString());
    }
  }
}
