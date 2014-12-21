package fr.vpm.wikipod.wiki.http.callback;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.vpm.wikipod.wiki.http.Searches;
import fr.vpm.wikipod.wiki.http.geosearch.Page;
import fr.vpm.wikipod.wiki.http.geosearch.Result;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vince on 02/12/14.
 */
public class GeosearchCb implements Callback<Result> {

  private final Searches searches;

  public GeosearchCb(Searches searches) {
    this.searches = searches;
  }

  @Override
  public void success(Result query, Response response) {
    Log.d("http", "success ids : " + query.toString());

    List<String> pageTitles = new ArrayList<String>();
    for (Page article : query.getQuery().getGeosearch()) {
      pageTitles.add(article.getTitle());
    }
    searches.searchPageTitles(pageTitles);
  }

  @Override
  public void failure(RetrofitError error) {
    Log.d("http", "failure " + error.toString());
  }

}
