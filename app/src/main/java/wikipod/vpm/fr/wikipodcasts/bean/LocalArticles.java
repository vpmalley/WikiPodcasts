package wikipod.vpm.fr.wikipodcasts.bean;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.vpm.wikipod.wiki.Article;

/**
 * Created by vince on 05/12/14.
 *
 * A simple data structure object. This encapsulates all data related to current location.
 */
public class LocalArticles extends ArrayList<Article> {

  private static final String ARTICLES_KEY = "ARTICLES";

  public LocalArticles(ArrayList<Article> articles) {
    super(articles);
  }
}
