package appnexus.api;

import static java.lang.String.format;
import appnexus.AccountDetails;
import appnexus.api.response.CampaignResponse;
import appnexus.api.response.IdResponse;
import appnexus.api.response.MemberResponse;
import appnexus.http.HttpClient;
import appnexus.model.Advertiser;
import appnexus.model.Campaign;
import appnexus.model.LineItem;
import appnexus.model.Member;

public class Api {
  
  protected final HttpClient httpClient;
  
  protected final AccountDetails accountDetails;
  
  protected ApiHelper helper = new ApiHelper();
  
  public Api(AccountDetails accountDetails) {
    this(HttpClient.DEFAULT, accountDetails);
  }
  
  public Api(HttpClient httpClient, AccountDetails accountDetails) {
    this.httpClient = httpClient;
    this.accountDetails = accountDetails;
    helper.init(this.httpClient, this.accountDetails);
  }
  
  public void auth() {
    helper.auth();
  }
  
  public Member getMember() {
    return helper.doGet(ApiPath.MEMBER, MemberResponse.class).getMember();
  }
  
  public void addAdvertiser(Advertiser advertiser) {
    String payload = advertiser.getAddAdvertiserJsonPayload();
    Integer id = helper.doPost(ApiPath.ADVERTISER, IdResponse.class, payload).getId();
    advertiser.setId(id);
  }
  
  public void addLineItem(LineItem lineItem) {
    String payload = lineItem.getAddLineItemJsonPayload();
    String url = format(ApiPath.LINE_ITEM_ADD, lineItem.getAdvertiserId());
    Integer id = helper.doPost(url, IdResponse.class, payload).getId();
    lineItem.setId(id);
  }
  
  public void addCampaign(Campaign campaign) {
    String payload = campaign.getAddCampaignJsonPayload();
    String url = format(ApiPath.CAMPAIGN_ADD, campaign.getAdvertiserId());
    Integer id = helper.doPost(url, IdResponse.class, payload).getId();
    campaign.setId(id);
  }
  
  public Campaign getCampaign(Integer campaignId, Integer advertiserId) {
    String url = format(ApiPath.CAMPAIGN_GET, campaignId, advertiserId);
    return helper.doGet(url, CampaignResponse.class).getCampaign();
  }

}
