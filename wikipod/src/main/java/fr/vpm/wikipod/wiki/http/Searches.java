package fr.vpm.wikipod.wiki.http;

import android.location.Location;

import java.util.List;

/**
 * Created by vince on 02/12/14.
 */
public interface Searches {

  /**
   * Search on Wikimedia project for geotagged articles around some location
   * @param location the location to search around
   * @param radius the radius (in meters) around the location, to search
   */
  void searchAround(Location location, int radius);

  /**
   * Search for the content of these pages
   * @param pageIds the ids of the pages to look up
   */
  void searchPageIds(List<String> pageIds);

  /**
   * Search for the content of these pages
   * @param titles the titles of the pages to look up
   */
  void searchPageTitles(List<String> titles);
}
