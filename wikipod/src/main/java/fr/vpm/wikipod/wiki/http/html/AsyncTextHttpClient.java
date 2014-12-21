package fr.vpm.wikipod.wiki.http.html;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 20/12/14.
 */
class AsyncTextHttpClient extends AsyncTask<String, Integer, List<String>> {

  private final TextHttpClient client;

  public AsyncTextHttpClient(TextHttpClient client) {
    this.client = client;
  }

  @Override
  protected List<String> doInBackground(String... urls) {
    List<String> contents = new ArrayList<String>();
    for (String url : urls){
      String content = client.get(url);
      if (content != null) {
        contents.add(content);
      }
    }
    return contents;
  }

}
