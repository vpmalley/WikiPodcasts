package fr.vpm.wikipod.wiki.http.callback;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.vpm.wikipod.wiki.Article;
import fr.vpm.wikipod.wiki.ArticleListener;
import fr.vpm.wikipod.wiki.http.api.Page;
import fr.vpm.wikipod.wiki.http.api.Query;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vince on 02/12/14.
 */
public class WikiApiCb implements Callback<Query> {

  private final ArticleListener listener;

  private final String wikisource;

  public WikiApiCb(ArticleListener listener, String wikisource) {
    this.listener = listener;
    this.wikisource = wikisource;
  }

  @Override
  public void success(Query query, Response response) {
    Log.d("http", "success content : " + query.toString());
    ArrayList<Article> articles = new ArrayList<Article>();
    for (Page p : query.getPages()) {
      Article article = new Article(wikisource, p.getTitle(), p.getRevisions().get(0).getContent());
      articles.add(article);
    }
    listener.onArticlesFound(articles);
  }

  @Override
  public void failure(RetrofitError error) {
    Log.d("http", "failure " + error.toString());
  }
}
