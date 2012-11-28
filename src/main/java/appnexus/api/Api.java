package appnexus.api;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.HashMap;
import java.util.Map;

import appnexus.AccountDetails;
import appnexus.Appnexus;
import appnexus.exception.AppnexusAuthException;
import appnexus.exception.AppnexusException;
import appnexus.http.HttpClient;
import appnexus.utils.JsonUtils;

public class Api {
  
  public interface ApiPath {
    public static final String BASE = Appnexus.getApiBase();  
    
    public static final String AUTH = format("%s/auth", BASE);
    
    public static final String MEMBER = format("%s/member", BASE);
    
    public static final String ADVERTISER = format("%s/advertiser", BASE);
    
    public static final String LINE_ITEM = format("%s/line-item", BASE);
    public static final String LINE_ITEM_ADD = LINE_ITEM + "?advertiser_id=%d";
    
    public static final String CAMPAIGN = format("%s/campaign", BASE);
    public static final String CAMPAIGN_ADD = CAMPAIGN + "?advertiser_id=%d";
    public static final String CAMPAIGN_GET = CAMPAIGN + "?id=%s&advertiser_id=%d";
  }
  
  protected HttpClient httpClient = HttpClient.DEFAULT;
  
  protected AccountDetails accountDetails;
  
  protected static final String JSON_RESPONSE_ROOT = "response";
  
  protected static final String AUTH_JSON_PAYLOAD = "{\"auth\": {\"username\" : \"%s\", \"password\" : \"%s\"}}";
  
  public Api(AccountDetails accountDetails) {
    this.accountDetails = accountDetails;
  }
  
  public void auth() {
    String payload = format(AUTH_JSON_PAYLOAD, accountDetails.getUsername(), accountDetails.getPassword());
    String token = doPost(ApiPath.AUTH, null, AuthResponse.class, payload).getToken();
    this.accountDetails.setAccessToken(token);
  }
  
  public Member getMember() {
    return doGet(ApiPath.MEMBER, MemberResponse.class).getMember();
  }
  
  public void addAdvertiser(Advertiser advertiser) {
    String payload = advertiser.getAddAdvertiserJsonPayload();
    Integer id = doPost(ApiPath.ADVERTISER, IdResponse.class, payload).getId();
    advertiser.setId(id);
  }
  
  public void addLineItem(LineItem lineItem) {
    String payload = lineItem.getAddLineItemJsonPayload();
    String url = format(ApiPath.LINE_ITEM_ADD, lineItem.getAdvertiserId());
    Integer id = doPost(url, IdResponse.class, payload).getId();
    lineItem.setId(id);
  }
  
  public void addCampaign(Campaign campaign) {
    String payload = campaign.getAddCampaignJsonPayload();
    String url = format(ApiPath.CAMPAIGN_ADD, campaign.getAdvertiserId());
    Integer id = doPost(url, IdResponse.class, payload).getId();
    campaign.setId(id);
  }
  
  public Campaign getCampaign(Integer campaignId, Integer advertiserId) {
    String url = format(ApiPath.CAMPAIGN_GET, campaignId, advertiserId);
    return doGet(url, CampaignResponse.class).getCampaign();
  }
  
  private <T extends Response> T doGet(String apiPath, Class<T> clazz) {
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

  protected void setHttpClient(HttpClient httpClient) {
    this.httpClient = httpClient;
  }
  
}
