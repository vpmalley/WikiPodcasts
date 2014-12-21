package fr.vpm.wikipod.wiki.http.geosearch;

/**
 * Created by vince on 29/11/14.
 */
public class Page {

  private long pageid;

  public String getPageId() {
    return String.valueOf(pageid);
  }

  public String getTitle() {
    return title;
  }

  private int ns;

  private String title;

  private float lat;

  private float lon;

  private float dist;

  private String primary;

  @Override
  public String toString() {
    return "Article : " + pageid + title;
  }
}
