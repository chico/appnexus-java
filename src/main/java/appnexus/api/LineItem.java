package appnexus.api;

import static java.lang.String.format;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class LineItem implements Serializable {

  private static final long serialVersionUID = 1L;
  
  protected static final String ADD_LINE_ITEM_JSON_PAYLOAD = "{\"line-item\": {\"name\": \"%s\", \"advertiser_id\": %s, \"revenue_type\": \"%s\", \"revenue_value\": %s}}";
  
  public interface RevenueType {
    // Do not track revenue for the line item.  
    public final static String NONE = "none";

    // A flat payment per 1000 impressions.
    public final static String CPM = "cpm";

    // A flat payment per click.
    public final static String CPC = "cpc";

    // A flat payment per conversion. 
    public final static String CPA = "cpa";

    // Media cost (what you spend on inventory) plus an extra CPM.
    public final static String COST_PLUS_CPM = "cost_plus_cpm";

    // Media cost (what you spend on inventory) plust a percentage of what you spend.
    public final static String COST_PLUS_MARGIN = "cost_plus_margin";

    // A flat payment that the advertiser will pays you on a specified allocation date.
    // If you pay managed publishers a percentage of your revenue, their share will be paid out on the allocation date,
    // after which the line item will no longer be editable.
    public final static String FLAT_FEE = "flat_fee";
  }

  private Integer id;
  
  private String name;
  
  private Integer advertiserId;
  
  private String revenueType;
  
  private String revenueValue;
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public Integer getAdvertiserId() {
    return advertiserId;
  }

  public void setAdvertiserId(Integer advertiserId) {
    this.advertiserId = advertiserId;
  }

  public String getRevenueType() {
    return revenueType;
  }

  public void setRevenueType(String revenueType) {
    this.revenueType = revenueType;
  }

  public String getRevenueValue() {
    return revenueValue;
  }

  public void setRevenueValue(String revenueValue) {
    this.revenueValue = revenueValue;
  }

  public String getAddLineItemJsonPayload() {
    // TODO use gson
    return format(ADD_LINE_ITEM_JSON_PAYLOAD, this.getName(), this.getAdvertiserId(), this.getRevenueType(), this.getRevenueValue());
  }

  @Override
  public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
  }
  
  public static class Builder {

    private Integer id;
    
    private String name;
    
    private Integer advertiserId;
    
    private String revenueType;
    
    private String revenueValue;
    
    public Builder id(Integer id) {
      this.id = id;
      return this;
    }
    
    public Builder name(String name) {
      this.name = name;
      return this;
    }
    
    public Builder advertiserId(Integer advertiserId) {
      this.advertiserId = advertiserId;
      return this;
    }
    
    public Builder revenueType(String revenueType) {
      this.revenueType = revenueType;
      return this;
    }
    
    public Builder revenueValue(String revenueValue) {
      this.revenueValue = revenueValue;
      return this;
    }
    
    public LineItem build() {
      LineItem lineItem = new LineItem();
      lineItem.id = this.id;
      lineItem.name = this.name;
      lineItem.advertiserId = this.advertiserId;
      lineItem.revenueType = revenueType;
      lineItem.revenueValue = revenueValue;
      return lineItem;
    }

  }
  
}
