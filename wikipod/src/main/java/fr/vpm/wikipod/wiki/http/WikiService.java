package fr.vpm.wikipod.wiki.http;

import fr.vpm.wikipod.wiki.http.geosearch.Result;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 *
 * Based on Retrofit library by Square (http://square.github.io/retrofit/), this interface is the facade to query HTTP API of Wikipedia
 * Created by vince on 29/11/14.
 */
public interface WikiService {


  //https://en.wikipedia.org/w/api.php?action=query&list=geosearch&gsradius=10000&gscoord=37.786971%7C-122.399677
  @GET("/w/api.php?action=query&list=geosearch&format=json")
  void getArticleIds(@Query("gsradius") String radius, @Query("gscoord") String coord, Callback<Result> resultListener);


  // https://en.wikipedia.org/w/api.php?action=query&titles=Main%20Page|Fksdlfsdss|Talk:&indexpageids&format=json&continue=
  //  http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=jsonfm&continue=&pageids=123|456
  @GET("/w/api.php?action=query&prop=revisions&rvprop=content&format=json&continue=")
  void getArticleContents(@Query("pageids") String pageIds, Callback<fr.vpm.wikipod.wiki.http.api.Query> resultListener);

}
