package appnexus.api;

import static java.lang.String.format;
import appnexus.Appnexus;

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