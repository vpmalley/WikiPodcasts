package fr.vpm.wikipod.wiki.http.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by vince on 02/12/14.
 */
public class Revision {

  private String contentformat;

  private String contentmodel;

  private String content;

  public Revision(String content, String contentformat, String contentmodel) {
    this.content = content;
    this.contentformat = contentformat;
    this.contentmodel = contentmodel;
  }

  @Override
  public String toString() {
    return "format:" + contentformat + ", model:" + contentmodel + ", content(partial)" + content.substring(0, 30);
  }

  public String getContent() {
    return content;
  }


  public static class RevisionDeserializer implements JsonDeserializer<Revision> {

    public RevisionDeserializer() {
    }

    @Override
    public Revision deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      JsonObject revision = json.getAsJsonObject();
      String content = revision.get("*").getAsString();
      String contentModel = revision.get("contentmodel").getAsString();
      String contentFormat = revision.get("contentformat").getAsString();
      return new Revision(content, contentFormat, contentModel);
    }
  }
}