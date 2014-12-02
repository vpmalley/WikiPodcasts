package fr.vpm.wikipod.wiki.http.callback;

import android.util.Log;

import fr.vpm.wikipod.wiki.http.api.Query;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vince on 02/12/14.
 */
public class WikiApiCb implements Callback<Query> {

  @Override
  public void success(Query query, Response response) {
    Log.d("http", "success");
    Log.d("http", "success content : " + query.toString());
    Log.d("http", "success rev : " + query.getPages().get(0).getRevisions().get(0).toString());

  }

  @Override
  public void failure(RetrofitError error) {
    Log.d("http", "failure " + error.toString());
  }
}
