package appnexus.api;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import appnexus.AccountDetails;
import appnexus.http.HttpClient;
import appnexus.model.Advertiser;
import appnexus.model.Campaign;
import appnexus.model.LineItem;

public class ApiUnitTest {
  
  private AccountDetails accountDetails = new AccountDetails.Builder()
    .username("username").password("password").accessToken(Fixtures.DUMMY_TOKEN).build();
  
  private Api api;
  
  private Map<String, String> authHeaders;
  
  @Mock private HttpClient mockHttpClient;
  
  @Before
  public void setUp() {    
    initMocks(this);
    this.api = new Api(mockHttpClient, accountDetails);
    this.authHeaders = api.helper.headers();
  }
  
  @Test
  public void testAuth() {
    String url = ApiPath.AUTH;
    String payload = format(ApiHelper.AUTH_JSON_PAYLOAD, accountDetails.getUsername(), accountDetails.getPassword());
    when(mockHttpClient.post(url, null, payload)).thenReturn(Fixtures.AUTH_RESPONSE);
    
    api.accountDetails.setAccessToken(null);
    api.auth();
    assertEquals(Fixtures.DUMMY_TOKEN, api.accountDetails.getAccessToken());
    
    verify(mockHttpClient, times(1)).post(url, null, payload);
  }
  
  @Test
  public void testGetMember() {    
    when(mockHttpClient.get(ApiPath.MEMBER, authHeaders)).thenReturn(Fixtures.MEMBER_RESPONSE);
    
    assertEquals(Fixtures.MEMBER, api.getMember());
    
    verify(mockHttpClient, times(1)).get(ApiPath.MEMBER, authHeaders);
  }
  
  @Test
  public void testAddAdvertiser() {
    String url = ApiPath.ADVERTISER;
    String payload = Fixtures.ADVERTISER.getAddAdvertiserJsonPayload();
    when(mockHttpClient.post(url, authHeaders, payload)).thenReturn(format(Fixtures.ID_RESPONSE, Fixtures.ADVERTISER.getId()));
    
    Advertiser advertiser = new Advertiser.Builder().name(Fixtures.ADVERTISER.getName()).state(Fixtures.ADVERTISER.getState()).build();
    api.addAdvertiser(advertiser);
    assertEquals(Fixtures.ADVERTISER, advertiser);
    
    verify(mockHttpClient, times(1)).post(url, authHeaders, payload);
  }
  
  @Test
  public void testAddLineItem() {
    String url = format(ApiPath.LINE_ITEM_ADD, Fixtures.LINE_ITEM.getAdvertiserId());
    String payload = Fixtures.LINE_ITEM.getAddLineItemJsonPayload();
    when(mockHttpClient.post(url, authHeaders, payload)).thenReturn(format(Fixtures.ID_RESPONSE, Fixtures.LINE_ITEM.getId()));
    
    LineItem lineItem = new LineItem.Builder()
      .name(Fixtures.LINE_ITEM.getName())
      .advertiserId(Fixtures.LINE_ITEM.getAdvertiserId())
      .revenueType(Fixtures.LINE_ITEM.getRevenueType())
      .revenueValue(Fixtures.LINE_ITEM.getRevenueValue())
      .build();
    api.addLineItem(lineItem);
    assertEquals(Fixtures.LINE_ITEM, lineItem);
    
    verify(mockHttpClient, times(1)).post(url, authHeaders, payload);
  }
  
  @Test
  public void testAddCampaign() {
    String url = format(ApiPath.CAMPAIGN_ADD, Fixtures.CAMPAIGN.getAdvertiserId());
    String payload = Fixtures.CAMPAIGN.getAddCampaignJsonPayload();
    when(mockHttpClient.post(url, authHeaders, payload)).thenReturn(format(Fixtures.ID_RESPONSE, Fixtures.CAMPAIGN.getId()));
    
    Campaign campaign = new Campaign.Builder()
      .name(Fixtures.CAMPAIGN.getName())
      .state(Fixtures.CAMPAIGN.getState())
      .advertiserId(Fixtures.CAMPAIGN.getAdvertiserId())
      .lineItemId(Fixtures.CAMPAIGN.getLineItemId())
      .inventoryType(Fixtures.CAMPAIGN.getInventoryType()).build();
    api.addCampaign(campaign);
    assertEquals(Fixtures.CAMPAIGN, campaign);
    
    verify(mockHttpClient, times(1)).post(url, authHeaders, payload);
  }
  
  @Test
  public void testGetCampaign() {
    String url = format(ApiPath.CAMPAIGN_GET, Fixtures.CAMPAIGN.getId(), Fixtures.CAMPAIGN.getAdvertiserId());
    when(mockHttpClient.get(url, authHeaders)).thenReturn(Fixtures.CAMPAIGN_RESPONSE);
    
    assertEquals(Fixtures.CAMPAIGN, api.getCampaign(Fixtures.CAMPAIGN.getId(), Fixtures.CAMPAIGN.getAdvertiserId()));
    
    verify(mockHttpClient, times(1)).get(url, authHeaders);
  }
  
  @Test
  public void testGetCampaigns() {
    String url = format(ApiPath.CAMPAIGNS, Fixtures.CAMPAIGN.getAdvertiserId());
    when(mockHttpClient.get(url, authHeaders)).thenReturn(Fixtures.CAMPAIGNS_RESPONSE);
    
    assertEquals(Fixtures.CAMPAIGNS, api.getCampaigns(Fixtures.CAMPAIGN.getAdvertiserId()));
    
    verify(mockHttpClient, times(1)).get(url, authHeaders);
  }
  
}