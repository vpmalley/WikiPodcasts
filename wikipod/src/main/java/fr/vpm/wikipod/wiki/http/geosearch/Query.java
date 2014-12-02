package fr.vpm.wikipod.wiki.http.geosearch;

import java.util.List;

/**
 * Created by vince on 02/12/14.
 */
public class Query {

  private List<Page> geosearch;

  public Query(List<Page> geosearch) {
    this.geosearch = geosearch;
  }

  @Override
  public String toString() {
    if (geosearch == null){
      return "geosearch is null";
    }
    return geosearch.toString();
  }

  public List<Page> getGeosearch() {
    return geosearch;
  }
}
