package fr.vpm.wikipod.wiki;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vince on 29/11/14.
 */
public class Article implements Parcelable {

  private static final String TITLE_KEY = "title";

  private static final String CONTENT_KEY = "content";

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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    Bundle b = new Bundle();
    b.putString(TITLE_KEY, title);
    b.putString(CONTENT_KEY, content);
    parcel.writeBundle(b);
  }

  private Article(Parcel in) {
    Bundle b = in.readBundle(Article.class.getClassLoader());
    // without setting the classloader, it fails on BadParcelableException : ClassNotFoundException when
    // unmarshalling Media class
    title = b.getString(TITLE_KEY);
    content = b.getString(CONTENT_KEY);
  }

  public static final Parcelable.Creator<Article> CREATOR
          = new Parcelable.Creator<Article>() {
    public Article createFromParcel(Parcel in) {
      return new Article(in);
    }

    public Article[] newArray(int size) {
      return new Article[size];
    }
  };
}
