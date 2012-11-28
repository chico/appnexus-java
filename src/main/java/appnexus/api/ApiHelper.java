package appnexus.api;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import appnexus.AccountDetails;
import appnexus.api.response.AuthResponse;
import appnexus.api.response.Response;
import appnexus.exception.AppnexusAuthException;
import appnexus.exception.AppnexusException;
import appnexus.http.HttpClient;
import appnexus.utils.BeanUtils;
import appnexus.utils.JsonUtils;

import com.google.gson.JsonElement;

public class ApiHelper {
  
  protected HttpClient httpClient;
  
  protected AccountDetails accountDetails;
  
  protected static final String JSON_RESPONSE_ROOT = "response";
  
  protected static final String AUTH_JSON_PAYLOAD = "{\"auth\": {\"username\" : \"%s\", \"password\" : \"%s\"}}";
  
  protected void init(HttpClient httpClient, AccountDetails accountDetails) {
    this.httpClient = httpClient;
    this.accountDetails = accountDetails;
  }

  protected void auth() {
    String payload = format(AUTH_JSON_PAYLOAD, accountDetails.getUsername(), accountDetails.getPassword());
    String token = doPost(ApiPath.AUTH, null, AuthResponse.class, payload).getToken();
    this.accountDetails.setAccessToken(token);
  }
  
  protected <T> T doGet(String apiPath, Class<T> clazz) {
    try {
      return parseAndCheckResponseFromJson(httpClient.get(apiPath, headers()), clazz);
    } catch (AppnexusAuthException e) {
      // initial call was not authenticated, try one more time
      return parseAndCheckResponseFromJson(httpClient.get(apiPath, headers()), clazz);
    }
  }
  
  protected <T> T doGet(String apiPath, String element, Type type) {
    try {
      return parseAndCheckResponseFromJson(httpClient.get(apiPath, headers()), element, type);
    } catch (AppnexusAuthException e) {
      // initial call was not authenticated, try one more time
      return parseAndCheckResponseFromJson(httpClient.get(apiPath, headers()), element, type);
    }
  }

  protected <T> T doPost(String apiPath, Class<T> clazz, String payload) {
    return doPost(apiPath, headers(), clazz, payload);
  }
  
  protected <T> T doPost(String apiPath, Map<String, String> headers, Class<T> clazz, String payload) {    
    try {
      return parseAndCheckResponseFromJson(httpClient.post(apiPath, headers, payload), clazz);
    } catch (AppnexusAuthException e) {
      // initial call was not authenticated, try one more time
      return parseAndCheckResponseFromJson(httpClient.post(apiPath, headers, payload), clazz);
    }
  }

  protected void checkResponseStatus(Response response) {
    if (!"OK".equalsIgnoreCase(response.getStatus())) {
      // Check if not logged in
      if ("NOAUTH".equalsIgnoreCase(response.getErrorId())) {
        throw new AppnexusAuthException(format("%s", response.getError()));
      }
      
      String msg = format("Failed request [status: %s, error: %s, error id: %s]",
          response.getStatus(), response.getError(), response.getErrorId());
      throw new AppnexusException(msg);
    }
  }
  
  protected Map<String, String> headers() {
    if (isBlank(accountDetails.getAccessToken())) {
      throw new RuntimeException("Token required in AccountDetails");
    }
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("Authorization", format("%s", accountDetails.getAccessToken()));
    return headers;
  }
  
  protected <T> T parseAndCheckResponseFromJson(String json, Class<T> clazz) {
    return parseAndCheckResponseFromJson(json, BeanUtils.getClassName(clazz), clazz);
  }
  
  protected <T> T parseAndCheckResponseFromJson(String json, String element, Class<T> clazz) {
    JsonElement rootElement = parseAndCheckResponseFromJson(json);
    if (Response.class.isAssignableFrom(clazz)) {
      return JsonUtils.fromJson(rootElement, clazz);
    }
    return JsonUtils.fromJson(rootElement, element, clazz);
  }
  
  protected <T> T parseAndCheckResponseFromJson(String json, String element, Type type) {
    JsonElement rootElement = parseAndCheckResponseFromJson(json);
    return JsonUtils.fromJson(rootElement, element, type);
  }

  protected JsonElement parseAndCheckResponseFromJson(String json) {
    if (isBlank(json)) {
      throw new IllegalArgumentException("json is blank");
    }
    JsonElement rootElement = JsonUtils.fromJsonToRootElement(json, JSON_RESPONSE_ROOT);
    Response response = JsonUtils.fromJson(rootElement, Response.class);
    try {
      checkResponseStatus(response);
    } catch (AppnexusAuthException e) {
      auth();
      throw e;
    }
    return rootElement;
  }

}
