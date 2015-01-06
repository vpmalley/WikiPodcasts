package wikipod.vpm.fr.wikipodcasts.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.regex.Pattern;

import fr.vpm.wikipod.location.Localisation;
import fr.vpm.wikipod.location.LocalisationListener;
import fr.vpm.wikipod.wiki.Article;
import fr.vpm.wikipod.wiki.ArticleListener;
import fr.vpm.wikipod.wiki.GeoArticles;
import fr.vpm.wikipod.wiki.http.GeoWiki;
import wikipod.vpm.fr.wikipodcasts.R;
import wikipod.vpm.fr.wikipodcasts.util.ProgressBarListener;

/**
 * Created by vince on 05/12/14.
 */
public class ArticleSearcher implements LocalisationListener, ArticleListener {

  public static final int DEFAULT_RADIUS = 5000; // the radius to search around, in meters

  private final Context context;

  private final ArticleListener articleListener;

  private final ProgressBarListener progressListener;

  public ArticleSearcher(Context context, ArticleListener articleListener, ProgressBarListener progressListener) {
    this.context = context;
    this.articleListener = articleListener;
    this.progressListener = progressListener;
  }

  @Override
  public void onLocalisationChanged(Localisation localisation) {
    searchAroundLocation(localisation);
  }

  public void searchAroundLocation(Localisation location) {
    progressListener.startRefreshProgress();
    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    String source = sharedPrefs.getString("pref_wiki_source", context.getString(R.string.pref_wiki_source_default));
    GeoArticles geoWiki = new GeoWiki(source);
    String radius = sharedPrefs.getString("pref_wiki_search_radius", "5000");
    if (!Pattern.compile("\\d+").matcher(radius).matches()){
      radius = "5000";
    }
    geoWiki.searchArticles(location, Integer.valueOf(radius), this);
  }

  @Override
  public void onArticlesFound(ArrayList<Article> articles) {
    progressListener.stopRefreshProgress();
    if (articles == null){
      articles = new ArrayList<>();
    }
    articleListener.onArticlesFound(articles);
  }
}
