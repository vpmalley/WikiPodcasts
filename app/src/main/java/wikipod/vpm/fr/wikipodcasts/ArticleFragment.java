package wikipod.vpm.fr.wikipodcasts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.vpm.wikipod.wiki.Article;
import wikipod.vpm.fr.wikipodcasts.util.ArticlePager;

/**
 * Created by vince on 05/12/14.
 */
public class ArticleFragment extends Fragment {


  private TextView contentView;

  private Article article;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_article, container, false);
    contentView = (TextView) v.findViewById(R.id.content);

    Bundle b = getArguments();
    if (b.containsKey(ArticlePager.ARTICLE_KEY)) {
      article = b.getParcelable(ArticlePager.ARTICLE_KEY);
      contentView.setText(article.getContent());
    }
    return v;
  }
}
