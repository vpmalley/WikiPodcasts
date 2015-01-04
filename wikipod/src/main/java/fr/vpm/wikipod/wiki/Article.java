package fr.vpm.wikipod.wiki;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.vpm.wikipod.location.Localisation;
import fr.vpm.wikipod.wiki.http.GeoWiki;

/**
 * Created by vince on 29/11/14.
 */
@DatabaseTable(tableName = "articles")
public class Article implements Parcelable {

  private static final String ID_KEY = "id";
  private static final String SOURCE_KEY = "source";
  private static final String TITLE_KEY = "title";

  private static final String CONTENT_KEY = "contentFile";
  private static final String WIKI_PATH = "/w/index.php?action=view&title=";

  @DatabaseField(generatedId = true)
  private long id;

  @DatabaseField(canBeNull = false)
  private long localisationId;

  @DatabaseField(canBeNull = false)
  private final String wikisource;

  @DatabaseField(canBeNull = false)
  private final String title;

  @DatabaseField(canBeNull = false)
  private final String contentFile;

  private Localisation localisation;

  Article(){
    wikisource = GeoWiki.EN_WIKIPEDIA;
    title = "";
    contentFile = "";
  }

  public Article(String wikisource, String title, String content) {
    this.wikisource = wikisource;
    this.title = title;
    this.contentFile = content;
  }

  public void setLocalisation(Localisation localisation) {
    localisationId = localisation.getDbId();
    this.localisation = localisation;
  }

  public Localisation getLocalisation() {
    return localisation;
  }

  public String getTitle(){
    return title;
  }

  public String getContentFile(){
    return contentFile;
  }

  public String getWebUrl() {
    return wikisource + WIKI_PATH + title;
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
    b.putLong(ID_KEY, id);
    b.putString(SOURCE_KEY, wikisource);
    b.putString(TITLE_KEY, title);
    b.putString(CONTENT_KEY, contentFile);
    parcel.writeBundle(b);
  }

  private Article(Parcel in) {
    Bundle b = in.readBundle(Article.class.getClassLoader());
    // without setting the classloader, it fails on BadParcelableException : ClassNotFoundException when
    // unmarshalling Media class
    id = b.getLong(ID_KEY);
    wikisource = b.getString(SOURCE_KEY);
    title = b.getString(TITLE_KEY);
    contentFile = b.getString(CONTENT_KEY);
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
