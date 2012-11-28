package appnexus.utils;

import java.lang.reflect.Type;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * Wrapper around gson
 */
public class JsonUtils {
  
  protected static final Gson gson = new GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .create();
  
  protected static final JsonParser parser = new JsonParser();

  public static <T> T fromJson(String json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }
  
  public static <T> T fromJson(String json, Type type) {
    return gson.fromJson(json, type);
  }
  
  public static <T> T fromJson(String json, String root, Class<T> classOfT) {
    JsonElement jsonElement = fromJsonToRootElement(json, root);
    return gson.fromJson(jsonElement, classOfT);
  }
  
  public static <T> T fromJson(JsonElement jsonElement, Class<T> classOfT) {
    return gson.fromJson(jsonElement, classOfT);
  }
  
  public static <T> T fromJson(JsonElement rootElement, String element, Class<T> classOfT) {
    JsonElement jsonElement = rootElement.getAsJsonObject().get(element);
    return gson.fromJson(jsonElement, classOfT);
  }
  
  public static <T> T fromJson(String json, String root, String element, Type type) {
    return fromJson(fromJsonToRootElement(json, root), element, type);
  }
  
  public static <T> T fromJson(JsonElement rootElement, String element, Type type) {
    JsonElement jsonElement = rootElement.getAsJsonObject().get(element);
    return gson.fromJson(jsonElement, type);
  }

  public static String toJson(Object src) {
    return gson.toJson(src);
  }
  
  public static String toJson(Object src, String root) {
    JsonObject json = new JsonObject();
    json.add(root, gson.toJsonTree(src));
    return json.toString();
  }
  
  public static Map<String, Object> toMap(String json) {
    // See https://sites.google.com/site/gson/gson-user-guide#TOC-Serializing-and-Deserializing-Generic-Types
    return gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());    
  }
  
  public static JsonElement fromJsonToRootElement(String json, String root) {
    if (StringUtils.isBlank(root)) {
      throw new IllegalArgumentException("root argument is mandatory; otherwise use fromJson(json, classOfT) instead");
    }
    JsonObject jsonObject = parser.parse(json).getAsJsonObject();    
    JsonElement jsonElement = jsonObject.get(root);
    return jsonElement;
  }
  
}
