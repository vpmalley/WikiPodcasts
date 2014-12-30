package wikipod.vpm.fr.wikipodcasts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.HashMap;

import fr.vpm.wikipod.wiki.Article;
import wikipod.vpm.fr.wikipodcasts.util.ArticlePager;

/**
 * Created by vince on 05/12/14.
 */
public class ArticleFragment extends Fragment {

  private WebView contentView;

  private Article article;

  private TextToSpeech tts;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_article, container, false);
    setHasOptionsMenu(true);
    contentView = (WebView) v.findViewById(R.id.content);

    Bundle b = getArguments();
    if (b.containsKey(ArticlePager.ARTICLE_KEY)) {
      article = b.getParcelable(ArticlePager.ARTICLE_KEY);
      contentView.loadData(article.getContent(), "text/html", "UTF-8");
      Log.d("content", article.getContent().substring(0, 400));
    }
    return v;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.article, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_podcast :
        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
          @Override
          public void onInit(int status) {
            tts.speak(article.getTitle(), TextToSpeech.QUEUE_ADD, new HashMap<String, String>());
          }
        });
        break;
      case R.id.action_browser :
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getWebUrl()));
        startActivity(i);
        break;
      default:
    }
    return super.onOptionsItemSelected(item);
  }
}
