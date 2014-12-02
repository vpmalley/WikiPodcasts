package fr.vpm.wikipod.wiki.http.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 02/12/14.
 */
public class Query {
  List<Page> pages;

  public Query(List<Page> pages) {
    this.pages = pages;
  }

  @Override
  public String toString() {
    if (pages == null){
      return "pages is null";
    }
    return pages.toString();
  }

  public List<Page> getPages() {
    return pages;
  }

  public static class QueryDeserializer implements JsonDeserializer<Query> {

    private List<String> pageIds;

    public QueryDeserializer(List<String> pageIds) {
      this.pageIds = pageIds;
    }

    @Override
    public Query deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      JsonObject pages = json.getAsJsonObject().getAsJsonObject("query").getAsJsonObject("pages"); // going down in the hierarchy
      List<Page> resultPages = new ArrayList<Page>();
      for (String pageId : pageIds){
        JsonElement page = pages.get(pageId);
        Page content = context.deserialize(page, Page.class);
        resultPages.add(content);
      }
      return new Query(resultPages);
    }
  }
}
