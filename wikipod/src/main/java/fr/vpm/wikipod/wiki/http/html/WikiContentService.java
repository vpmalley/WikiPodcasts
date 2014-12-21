package fr.vpm.wikipod.wiki.http.html;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.vpm.wikipod.wiki.Article;
import fr.vpm.wikipod.wiki.ArticleListener;

/**
 * Created by vince on 20/12/14.
 */
public class WikiContentService extends AsyncTextHttpClient {

  private static final String PATH = "/w/index.php?action=view&title=";

  private final String wikisource;

  private final ArticleListener listener;

  private final List<String> articleTitles = new ArrayList<String>();

  public WikiContentService(TextHttpClient client, String wikisource, ArticleListener listener) {
    super(client);
    this.wikisource = wikisource;
    this.listener = listener;
  }

  public void searchTitles(String... titles){
    String[] urls = new String[titles.length];
    for (int i = 0; i < titles.length; i++){
      urls[i] = wikisource + PATH + titles[i];
      articleTitles.add(titles[i]);
    }
    executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urls);
  }

  @Override
  protected void onPostExecute(List<String> contents) {
    List<Article> articles = new ArrayList<>();
    for (int i = 0; i < contents.size(); i++) {
      String content = contents.get(i);
      Log.d("web", "content received : " + content.substring(0, Math.min(content.length(), 20)));
      articles.add(new Article(articleTitles.get(i), content));
    }
    listener.onArticlesFound(articles);
  }

}
