package appnexus;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import appnexus.model.Advertiser;
import appnexus.model.Campaign;
import appnexus.model.LineItem;
import appnexus.model.Member;

public class AppnexusTest {
  
  protected Logger logger = LoggerFactory.getLogger(AppnexusTest.class);
  
  private static final String USERNAME = System.getProperty("appnexus.username");
  
  private static final String PASSWORD = System.getProperty("appnexus.password");
  
  private static final Integer SANDBOX_ADVERTISER_ID = 57440;
  
  private static final Integer SANDBOX_LINE_ITEM_ID = 64880;
  
  private static final Integer SANDBOX_CAMPAIGN_ID = 104182;
  
  @BeforeClass
  public static void setup() {
    Appnexus.initSandbox(USERNAME, PASSWORD);
  }
  
  @Test
  public void testAccessTokenHasBeenSet() {
    assertNotNull(Appnexus.accountDetails.getAccessToken());
  }
  
  @Test
  public void testGetMember() {
    Member member = Appnexus.api.getMember();
    assertNotNull(member.getId());
    assertNotNull(member.getName());
  }
  
  @Test
  public void testGetCampaign() {
    Campaign campaign = Appnexus.api.getCampaign(SANDBOX_CAMPAIGN_ID, SANDBOX_ADVERTISER_ID);
    assertEquals(SANDBOX_CAMPAIGN_ID, campaign.getId());
    assertEquals(SANDBOX_ADVERTISER_ID, campaign.getAdvertiserId());
    assertEquals(SANDBOX_LINE_ITEM_ID, campaign.getLineItemId());
    assertNotNull(campaign.getName());
  }
  
  @Test
  public void testGetCampaigns() {
    List<Campaign> campaigns = Appnexus.api.getCampaigns(SANDBOX_ADVERTISER_ID);
    assertEquals(SANDBOX_CAMPAIGN_ID, campaigns.get(0).getId());
  }
  
  @Test
  @Ignore
  public void testAddAdvertiser() {    
    Advertiser advertiser = new Advertiser.Builder().name("test").state(Advertiser.State.ACTIVE).build();
    Appnexus.api.addAdvertiser(advertiser);
    assertNotNull(advertiser.getId());
    logger.info(format("Advertiser created (id: %s)", advertiser.getId()));
  }
  
  @Test
  @Ignore
  public void testAddLineItem() {    
    LineItem lineItem = new LineItem.Builder()
      .name("test")
      .advertiserId(SANDBOX_ADVERTISER_ID)      
      .revenueType(LineItem.RevenueType.CPM)
      .revenueValue("5")
      .build();
    Appnexus.api.addLineItem(lineItem);
    assertNotNull(lineItem.getId());
    logger.info(format("Line Item created (id: %d)", lineItem.getId()));
  }
  
  @Test
  @Ignore
  public void testAddCampaign() {    
    Campaign campaign = new Campaign.Builder()
      .name("test").state(Campaign.State.ACTIVE)
      .advertiserId(SANDBOX_ADVERTISER_ID)
      .lineItemId(SANDBOX_LINE_ITEM_ID)
      .inventoryType(Campaign.InventoryType.DIRECT)
      .build();
    Appnexus.api.addCampaign(campaign);
    assertNotNull(campaign.getId());
    logger.info(format("Campaign created (id: %d)", campaign.getId()));
  }

}