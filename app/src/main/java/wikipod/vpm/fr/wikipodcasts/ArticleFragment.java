package wikipod.vpm.fr.wikipodcasts;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

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
  public void onCreate(Bundle savedInstanceState) {
    //setHasOptionsMenu(true);
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_article, container, false);
    setHasOptionsMenu(true);
    contentView = (WebView) v.findViewById(R.id.content);

    Bundle b = getArguments();
    if (b.containsKey(ArticlePager.ARTICLE_KEY)) {
      article = b.getParcelable(ArticlePager.ARTICLE_KEY);
      contentView.loadData(article.getContent(), "text/html", null);
      Log.d("content", article.getContent().substring(0, 100));
    }
    return v;
  }
}
