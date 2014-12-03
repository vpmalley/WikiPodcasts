package fr.vpm.wikipod.wiki;

/**
 * Created by vince on 29/11/14.
 */
public class Article {

  private final String title;

  private final String content;

  public Article(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public String getTitle(){
    return title;
  }

  public String getContent(){
    return content;
  }

  @Override
  public String toString() {
    return getTitle();
  }
}
