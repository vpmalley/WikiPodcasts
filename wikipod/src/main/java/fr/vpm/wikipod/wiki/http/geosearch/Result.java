package fr.vpm.wikipod.wiki.http.geosearch;

/**
 * Created by vince on 02/12/14.
 */
public class Result {

  private Query query;

  public Result(Query query) {
    this.query = query;
  }

  @Override
  public String toString() {
    if (query == null){
      return "query is null";
    }
    return query.toString();
  }

  public Query getQuery() {
    return query;
  }
}
