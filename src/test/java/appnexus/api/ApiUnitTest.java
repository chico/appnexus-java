package appnexus.api;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import appnexus.AccountDetails;
import appnexus.http.HttpClient;

public class ApiUnitTest {
  
  private AccountDetails accountDetails = new AccountDetails.Builder()
    .username("username").password("password").accessToken(Fixtures.DUMMY_TOKEN).build();
  
  private Api api = new Api(accountDetails);
  
  @Mock private HttpClient mockHttpClient;
  
  @Before
  public void setUp() {    
    initMocks(this);
    api.setHttpClient(mockHttpClient);
  }
  
  @Test
  public void testAuth() {
    String url = Api.ApiPath.AUTH;
    String payload = format(Api.AUTH_JSON_PAYLOAD, accountDetails.getUsername(), accountDetails.getPassword());
    when(mockHttpClient.post(url, null, payload)).thenReturn(Fixtures.AUTH_RESPONSE);
    
    api.accountDetails.setAccessToken(null);
    api.auth();
    assertEquals(Fixtures.DUMMY_TOKEN, api.accountDetails.getAccessToken());
    
    verify(mockHttpClient, times(1)).post(url, null, payload);
  }
  
  @Test
  public void testGetMember() {
    when(mockHttpClient.get(Api.ApiPath.MEMBER, api.headers())).thenReturn(Fixtures.MEMBER_RESPONSE);
    
    assertEquals(Fixtures.MEMBER, api.getMember());
    
    verify(mockHttpClient, times(1)).get(Api.ApiPath.MEMBER, api.headers());
  }
  
  @Test
  public void testAddAdvertiser() {
    String url = Api.ApiPath.ADVERTISER;
    String advertiserName = Fixtures.ADVERTISER.getName();
    String advertiserState = Fixtures.ADVERTISER.getState();
    String payload = format(Api.ADD_ADVERTISER_JSON_PAYLOAD, advertiserName, advertiserState);
    when(mockHttpClient.post(url, api.headers(), payload)).thenReturn(format(Fixtures.ID_RESPONSE, Fixtures.ADVERTISER.getId()));
    
    Advertiser advertiser = new Advertiser.Builder().name(advertiserName).state(advertiserState).build();
    api.addAdvertiser(advertiser);
    assertEquals(Fixtures.ADVERTISER, advertiser);
    
    verify(mockHttpClient, times(1)).post(url, api.headers(), payload);
  }
  
}