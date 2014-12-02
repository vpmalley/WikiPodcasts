package fr.vpm.wikipod.wiki.http.api;

import java.util.List;

/**
 * Created by vince on 30/11/14.
 */
public class Page {

  long pageid;

  int ns;

  String title;

  List<Revision> revisions;

  @Override
  public String toString() {
    return "content has title : " + title;
  }

  public List<Revision> getRevisions() {
    return revisions;
  }
}
