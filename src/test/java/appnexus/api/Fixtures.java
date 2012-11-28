package appnexus.api;

import appnexus.model.Advertiser;
import appnexus.model.Campaign;
import appnexus.model.LineItem;
import appnexus.model.Member;

public interface Fixtures {
  
  public final static String DUMMY_TOKEN = "fiuho88jtuu5qef9ofmglbmcn7";

  public final static String AUTH_RESPONSE = "{\n" + 
  		"    \"response\": {\n" + 
  		"        \"status\": \"OK\", \n" + 
  		"        \"token\": \"" + DUMMY_TOKEN + "\"\n" + 
  		"    }\n" + 
  		"}";
  
  public final static String MEMBER_RESPONSE = "{\n" + 
  		"    \"response\": {\n" + 
  		"        \"member\": {\n" + 
  		"            \"developer_id\": null,\n" + 
  		"            \"id\": 2182, \n" + 
  		"            \"name\": \"HulkMedia\", \n" + 
  		"            \"state\": \"active\", \n" + 
  		"            \"timezone\": \"EST5EDT\"\n" + 
  		"        }, \n" + 
  		"        \"status\": \"OK\"\n" + 
  		"    }\n" + 
  		"}";
  
  public final static String CAMPAIGN_RESPONSE = "{\n" + 
      "    \"response\": {\n" + 
      "        \"status\": \"OK\",\n" + 
      "        \"campaign\": {\n" + 
      "            \"id\": 1234,\n" + 
      "            \"state\": \"active\",\n" + 
      "            \"advertiser_id\": 57440,\n" + 
      "            \"name\": \"test\",\n" + 
      "            \"line_item_id\": 3467,\n" + 
      "            \"start_date\": \"2011-10-24 00:00:00\",\n" + 
      "            \"end_date\": null,\n" + 
      "            \"timezone\": \"EST5EDT\",\n" + 
      "            \"lifetime_budget\": 6000,\n" + 
      "            \"lifetime_budget_imps\": null,\n" + 
      "            \"daily_budget\": null,\n" + 
      "            \"daily_budget_imps\": null,\n" + 
      "            \"enable_pacing\": false,\n" + 
      "            \"inventory_type\": \"direct\",\n" + 
      "            \"last_modified\": \"2012-02-14 16:13:40\"\n" + 
      "        }\n" + 
      "    }\n" + 
      "}";
  
  public final static String ID_RESPONSE = "{\"response\":{\"status\":\"OK\", \"id\":%d}}";
  
  public final static Member MEMBER = new Member.Builder().name("HulkMedia").id("2182").build();
  
  public final static Advertiser ADVERTISER = new Advertiser.Builder().name("HulkMedia").state(Advertiser.State.ACTIVE).id(1234).build();
  
  public final static Campaign CAMPAIGN = new Campaign.Builder().name("test").state(Campaign.State.ACTIVE).advertiserId(57440).lineItemId(3467).inventoryType(Campaign.InventoryType.DIRECT).id(1234).build();
  
  public final static LineItem LINE_ITEM = new LineItem.Builder().name("test").advertiserId(57440).revenueType(LineItem.RevenueType.CPM).revenueValue("5").id(1234).build();

}
