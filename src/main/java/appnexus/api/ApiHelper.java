package appnexus.api;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.HashMap;
import java.util.Map;

import appnexus.AccountDetails;
import appnexus.api.ApiPath;
import appnexus.api.response.AuthResponse;
import appnexus.api.response.Response;
import appnexus.exception.AppnexusAuthException;
import appnexus.exception.AppnexusException;
import appnexus.http.HttpClient;
import appnexus.utils.JsonUtils;

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
  
  protected <T extends Response> T doGet(String apiPath, Class<T> clazz) {
    T response = fromJson(httpClient.get(apiPath, headers()), clazz);
    try {
      checkResponseStatus(response);
    } catch (AppnexusAuthException e) {
      // Do auth and try one more time
      auth();
      response = fromJson(httpClient.get(apiPath, headers()), clazz);
      checkResponseStatus(response);
    }
    return response;
  }

  protected <T extends Response> T doPost(String apiPath, Class<T> clazz, String payload) {
    return doPost(apiPath, headers(), clazz, payload);
  }
  
  protected <T extends Response> T doPost(String apiPath, Map<String, String> headers, Class<T> clazz, String payload) {
    String result = httpClient.post(apiPath, headers, payload);    
    T response = fromJson(result, clazz);
    try {
      checkResponseStatus(response);
    } catch (AppnexusAuthException e) {
      // Do auth and try one more time
      auth();
      response = fromJson(httpClient.post(apiPath, headers, payload), clazz);
      checkResponseStatus(response);
    }
    return response;
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
  
  protected <T> T fromJson(String json, Class<T> clazz) {
    if (isBlank(json)) {
      throw new IllegalArgumentException("json is blank");
    }
    return JsonUtils.fromJson(json, JSON_RESPONSE_ROOT, clazz);
  }

}
