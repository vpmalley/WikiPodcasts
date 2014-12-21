package fr.vpm.wikipod.wiki.http.api;

/**
 * Created by vince on 30/11/14.
 */
public class WebPage {

  String content;

  @Override
  public String toString() {
    return "web content is : " + content;
  }

  public String getContent() {
    return content;
  }
}
