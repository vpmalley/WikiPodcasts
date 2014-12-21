package wikipod.vpm.fr.wikipodcasts.bean;

import java.util.ArrayList;

import fr.vpm.wikipod.wiki.Article;

/**
 * Created by vince on 05/12/14.
 *
 * A simple data structure object. This encapsulates all data related to current location.
 */
public class LocalArticles extends ArrayList<Article> {

  private static final String ARTICLES_KEY = "ARTICLES";

  public LocalArticles(ArrayList<Article> articles) {
    super(articles);
  }
}
