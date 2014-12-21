package wikipod.vpm.fr.wikipodcasts.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import fr.vpm.wikipod.wiki.Article;
import wikipod.vpm.fr.wikipodcasts.ArticleFragment;

/**
 * Created by vince on 05/12/14.
 */
public class ArticlePager extends FragmentStatePagerAdapter {

  public static final String ARTICLE_KEY = "article";
  public static final String ARTICLES_KEY = "articles";
  public static final String INITIAL_POS_KEY = "initPos";

  private ArrayList<Article> articles;

  public ArticlePager(FragmentManager fm) {
    super(fm);
  }

  public void setArticles(ArrayList<Article> articles){
    this.articles = articles;
  }

  @Override
  public Fragment getItem(int position) {
    Fragment fragment = new ArticleFragment();
    Bundle b = new Bundle();
    Article article = articles.get(position);
    b.putParcelable(ARTICLE_KEY, article);
    fragment.setArguments(b);
    return fragment;
  }

  @Override
  public int getCount() {
    return articles.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return articles.get(position).getTitle();
  }
}
