package fr.vpm.wikipod.wiki.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import fr.vpm.wikipod.Constants;
import fr.vpm.wikipod.location.Localisation;
import fr.vpm.wikipod.wiki.ArticleListener;
import fr.vpm.wikipod.wiki.GeoArticles;
import fr.vpm.wikipod.wiki.http.api.Query;
import fr.vpm.wikipod.wiki.http.api.Revision;
import fr.vpm.wikipod.wiki.http.callback.GeosearchCb;
import fr.vpm.wikipod.wiki.http.callback.WikiApiCb;
import fr.vpm.wikipod.wiki.http.html.HtmlClient;
import fr.vpm.wikipod.wiki.http.html.WikiContentService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Provides a list of Wiki articles based on a location. This is the entry-point for this module.
 *
 * Created by vince on 29/11/14.
 */
public class GeoWiki implements GeoArticles, Searches {

  private ArticleListener listener;


  public static final String EN_WIKIPEDIA = "https://en.wikipedia.org";
  /**
   * Which source to use (including language) for the requests
   */
  private final String wikisource;

  public GeoWiki(String wikisource) {
    if (wikisource == null || wikisource.isEmpty()){
      wikisource = EN_WIKIPEDIA;
    }
    this.wikisource = wikisource;
  }

  @Override
  public Status searchArticles(Localisation location, int radius, ArticleListener listener) {
    this.listener = listener;
    if (radius <= 0) {
      radius = Constants.LOCATION_RADIUS;
    } else if (radius > Constants.LOCATION_RADIUS) {
      radius = Constants.LOCATION_RADIUS;
    }
    searchAround(location, radius);
    return Status.SEARCH_IN_PROGRESS;
  }

  @Override
  public void searchAround(Localisation location, int radius) {
    WikiService wService = getWikiService(wikisource, new ArrayList<String>());
    wService.getArticleIds(String.valueOf(radius), formatLocation(location), new GeosearchCb(this));
  }

  @Override
  public void searchPageIds(List<String> pageIds) {
    // now, query the articles
    WikiService wService = getWikiService(wikisource, pageIds);

    String pageIdsParam = "";
    for (String pageId : pageIds) {
      pageIdsParam += '|';
      pageIdsParam += pageId;
    }
    pageIdsParam = pageIdsParam.substring(1);
    wService.getArticleContents(pageIdsParam, new WikiApiCb(listener, wikisource));
  }

  @Override
  public void searchPageTitles(List<String> titles) {
    new WikiContentService(new HtmlClient(), wikisource, listener).searchTitles(titles.toArray(new String[titles.size()]));
  }

  /**
   * Formats the location into Mediawiki request path parameter
   * @param location the location to transform
   * @return the transformed location, ready to append to a Mediawiki query
   */
  private String formatLocation(Localisation location) {
    String lat = String.valueOf(location.getLatitude());
    String lon = String.valueOf(location.getLongitude());
    return lat + "|" + lon;
  }

  private WikiService getWikiService(String wikisource, List<String> pageIds) {
    Gson gson = new GsonBuilder().registerTypeAdapter(Query.class, new Query.QueryDeserializer(pageIds)).
            registerTypeAdapter(Revision.class, new Revision.RevisionDeserializer()).create();

    RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(wikisource)
            .setConverter(new GsonConverter(gson))
            .build();
    return restAdapter.create(WikiService.class);
  }
}
