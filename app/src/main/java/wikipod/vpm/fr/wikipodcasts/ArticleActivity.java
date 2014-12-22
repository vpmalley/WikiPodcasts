package wikipod.vpm.fr.wikipodcasts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import fr.vpm.wikipod.wiki.Article;
import wikipod.vpm.fr.wikipodcasts.util.ArticlePager;

/**
 * Created by vince on 07/12/14.
 */
public class ArticleActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_content);

    ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

    Intent i = getIntent();
    if (i.hasExtra(ArticlePager.ARTICLES_KEY)) {
      ArrayList<Article> articles = i.getParcelableArrayListExtra(ArticlePager.ARTICLES_KEY);
      ArticlePager articleAdapter = new ArticlePager(getSupportFragmentManager());
      articleAdapter.setArticles(articles);
      viewPager.setAdapter(articleAdapter);
      viewPager.setCurrentItem(0);
    }
    if (i.hasExtra(ArticlePager.INITIAL_POS_KEY)){
      int initPos = i.getIntExtra(ArticlePager.INITIAL_POS_KEY, -1);
      viewPager.setCurrentItem(initPos);
    }

  }

}
