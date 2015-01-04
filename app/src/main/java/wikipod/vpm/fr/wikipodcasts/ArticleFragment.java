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

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
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
      InputStream contentStream = null;
      String result = "";
      try {
        contentStream = new FileInputStream(new File(article.getContentFile()));
        StringWriter writer = new StringWriter();
        IOUtils.copy(contentStream, writer, "UTF-8");
        result = writer.toString();
      } catch (IOException e) {
        Log.w("file", "could not load file content");
      } finally {
        if (contentStream != null) {
          try {
            contentStream.close();
          } catch (IOException e) {
            Log.w("file", "could not load file content");
          }
        }
      }
      contentView.loadDataWithBaseURL(null, result, "text/html", "utf-8", null);
      Log.d("content", article.getContentFile());
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
