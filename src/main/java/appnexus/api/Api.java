package appnexus.api;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import appnexus.AccountDetails;
import appnexus.api.response.IdResponse;
import appnexus.http.HttpClient;
import appnexus.model.Advertiser;
import appnexus.model.Campaign;
import appnexus.model.LineItem;
import appnexus.model.Member;
import appnexus.utils.BeanUtils;

import com.google.gson.reflect.TypeToken;

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
    return helper.doGet(ApiPath.MEMBER, Member.class);
  }
  
  public void addAdvertiser(Advertiser advertiser) {
    String payload = advertiser.toJson();
    Integer id = helper.doPost(ApiPath.ADVERTISER, IdResponse.class, payload).getId();
    advertiser.setId(id);
  }
  
  public void addLineItem(LineItem lineItem) {
    String payload = lineItem.toJson();
    String url = format(ApiPath.LINE_ITEM_ADD, lineItem.getAdvertiserId());
    Integer id = helper.doPost(url, IdResponse.class, payload).getId();
    lineItem.setId(id);
  }
  
  public void addCampaign(Campaign campaign) {
    String payload = campaign.toJson();
    String url = format(ApiPath.CAMPAIGN_ADD, campaign.getAdvertiserId());
    Integer id = helper.doPost(url, IdResponse.class, payload).getId();
    campaign.setId(id);
  }
  
  public Campaign getCampaign(Integer campaignId, Integer advertiserId) {
    String url = format(ApiPath.CAMPAIGN_GET, campaignId, advertiserId);
    return helper.doGet(url, Campaign.class);
  }
  
  public List<Campaign> getCampaigns(Integer advertiserId) {
    String url = format(ApiPath.CAMPAIGNS, advertiserId);
    return helper.doGet(url, BeanUtils.getPluralizedClassName(Campaign.class), new TypeToken<ArrayList<Campaign>>(){}.getType());
  }
  
}
