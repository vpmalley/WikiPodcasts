package fr.vpm.wikipod.wiki.http.html;

import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    File articleFile = getArticleFile(url);
    try {
      URL formattedUrl = new URL(url);
      urlConnection = (HttpURLConnection) formattedUrl.openConnection();
      InputStream in = new BufferedInputStream(urlConnection.getInputStream());

      OutputStream fileStream = new FileOutputStream(articleFile);
      StringWriter writer = new StringWriter();
      IOUtils.copy(in, fileStream);//, urlConnection.getContentEncoding());
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
    return articleFile.getAbsolutePath();
  }

  private File getArticleFile(String url) {
    String fileName = url;
    fileName = fileName.replace("http://", "").replace("https://", "");
    fileName = fileName.replace("/", "-");
    File appFolder = new File(Environment.getExternalStorageDirectory(), "wikipod");
    File articlesFolder = new File(appFolder, "articles");
    articlesFolder.mkdirs();
    File articleFile = new File(articlesFolder, fileName);
    return articleFile;
  }
}
