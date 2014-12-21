package fr.vpm.wikipod.wiki.http.html;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vince on 19/12/14.
 */
public class HtmlClient implements TextHttpClient {

  @Override
  public String get(String url) {
    HttpURLConnection urlConnection = null;
    String result = null;
    try {
      URL formattedUrl = new URL(url);
      urlConnection = (HttpURLConnection) formattedUrl.openConnection();
      InputStream in = new BufferedInputStream(urlConnection.getInputStream());
      // close
      StringWriter writer = new StringWriter();
      IOUtils.copy(in, writer);
      result = writer.toString();
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
      Log.w("http", e.toString());
    }
    finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
    }
    return result;
  }
}
