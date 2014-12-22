package wikipod.vpm.fr.wikipodcasts.search;

import android.content.Context;
import android.location.Geocoder;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

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

  private final Context context;

  private final AbsListView articlesView;

  private final ProgressBarListener progressListener;

  public ArticleSearcher(Context context, AbsListView articlesView, ProgressBarListener progressListener) {
    this.context = context;
    this.articlesView = articlesView;
    this.progressListener = progressListener;
  }

  @Override
  public void onLocationChanged(Localisation localisation) {
    String geocodingInfo = "no geocoding implem";
    if (Geocoder.isPresent()){
      geocodingInfo = localisation.getNearbyAddresses().get(0).getLocality();
    }
    Toast.makeText(context, "received localisation " + localisation.getLatitude() + ", " +
            localisation.getLongitude() + ", " + geocodingInfo, Toast.LENGTH_SHORT).show();
    GeoArticles geoWiki = new GeoWiki("");
    geoWiki.searchArticles(localisation, 5000, this);
  }

  @Override
  public void onArticlesFound(List<Article> articles) {
    String result = "";
    if (articles != null) {
      result = articles.toString();
      if (articlesView != null) {
        ArrayAdapter<Article> articlesAdapter = new ArrayAdapter<Article>(context, R.layout.list_item, articles);
        articlesView.setAdapter(articlesAdapter);
      }
    }
    progressListener.stopRefreshProgress();
    Toast.makeText(context, "received articles " + result, Toast.LENGTH_SHORT).show();
  }
}
