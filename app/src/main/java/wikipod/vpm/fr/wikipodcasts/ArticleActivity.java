package wikipod.vpm.fr.wikipodcasts;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import fr.vpm.wikipod.wiki.Article;
import wikipod.vpm.fr.wikipodcasts.util.ArticlePager;

/**
 * Created by vince on 07/12/14.
 */
public class ArticleActivity extends ActionBarActivity {

  private TextToSpeech tts;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_content);
    //getActionBar().setDisplayHomeAsUpEnabled(true);

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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show();
    getMenuInflater().inflate(R.menu.article, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_podcast :
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
          @Override
          public void onInit(int status) {
            //tts.speak("Hello World", TextToSpeech.QUEUE_ADD, new Bundle(), "helloworld");
            tts.speak("Hello World", TextToSpeech.QUEUE_ADD, new HashMap<String, String>());
          }
        });
        break;
      default:
    }
    return super.onOptionsItemSelected(item);
  }


}
