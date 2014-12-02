package fr.vpm.wikipod.wiki.http.api;

/**
 * Created by vince on 02/12/14.
 */
public class Result {
  Query query;

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
}

