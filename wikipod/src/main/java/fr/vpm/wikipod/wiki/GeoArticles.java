package fr.vpm.wikipod.wiki;

import fr.vpm.wikipod.location.Localisation;

/**
 * Provides a list of informative articles based on a location
 *
 * Created by vince on 29/11/14.
 */
public interface GeoArticles {

  public enum Status {
    SEARCH_IN_PROGRESS,
    LOCATION_DISABLED,
    INTERNET_DISABLED
  }

  /**
   * Searches for articles around a {@link android.location.Location}
   *
   * @param location The location to search around for articles
   * @param radius radius of the search (in meters)
   * @return the {@link Status} for the current situation
   */
  public Status searchArticles(Localisation location, int radius, ArticleListener listener);
}
