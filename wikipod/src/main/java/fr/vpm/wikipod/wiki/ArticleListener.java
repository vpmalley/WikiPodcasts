package fr.vpm.wikipod.wiki;

import java.util.List;

/**
 * Listener for informative articles retrieval.
 *
 * Created by vince on 29/11/14.
 */
public interface ArticleListener {

  /**
   * When articles are received, all {@link fr.vpm.wikipod.wiki.ArticleListener} will be notified with this method
   * @param articles the list of articles received
   */
  void onArticlesFound(List<Article> articles);

}
