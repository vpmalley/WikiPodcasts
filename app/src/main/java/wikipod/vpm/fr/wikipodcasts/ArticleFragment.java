package wikipod.vpm.fr.wikipodcasts;

import android.hardware.camera2.params.LensShadingMap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import fr.vpm.wikipod.wiki.Article;
import wikipod.vpm.fr.wikipodcasts.util.ArticlePager;

/**
 * Created by vince on 05/12/14.
 */
public class ArticleFragment extends Fragment {


  private TextView contentView;

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
    contentView = (TextView) v.findViewById(R.id.content);
    setHasOptionsMenu(true);

    Bundle b = getArguments();
    if (b.containsKey(ArticlePager.ARTICLE_KEY)) {
      article = b.getParcelable(ArticlePager.ARTICLE_KEY);
      contentView.setText(Html.fromHtml(article.getContent()));
    }
    return v;
  }
}
