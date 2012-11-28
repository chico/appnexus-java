package appnexus.model;

import static java.lang.String.format;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Campaign implements Serializable {

  private static final long serialVersionUID = 1L;
  
  protected static final String ADD_CAMPAIGN_JSON_PAYLOAD = "{\"campaign\": {\"name\": \"%s\", \"state\": \"%s\", \"advertiser_id\": %d, \"line_item_id\": %d, \"inventory_type\": \"%s\"}}";
  
  public interface State {
    public final static String ACTIVE = "active";
    public final static String INACTIVE = "inactive";
  }
  
  public interface InventoryType {
    public final static String DIRECT = "direct";
    public final static String FACEBOOK = "facebook";
  }

  private Integer id;
  
  private String name;
  
  private String state;
  
  private Integer advertiserId;
  
  private Integer lineItemId;
  
  private String inventoryType;
  
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
  
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
  
  public Integer getAdvertiserId() {
    return advertiserId;
  }

  public void setAdvertiserId(Integer advertiserId) {
    this.advertiserId = advertiserId;
  }
  
  public Integer getLineItemId() {
    return lineItemId;
  }

  public void setLineItemId(Integer lineItemId) {
    this.lineItemId = lineItemId;
  }

  public String getInventoryType() {
    return inventoryType;
  }

  public void setInventoryType(String inventoryType) {
    this.inventoryType = inventoryType;
  }
  
  public String getAddCampaignJsonPayload() {
    // TODO use gson
    return format(ADD_CAMPAIGN_JSON_PAYLOAD, this.getName(), this.getState(), this.getAdvertiserId(), this.getLineItemId(), this.getInventoryType());
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
    
    private String state;
    
    private Integer advertiserId;
    
    private Integer lineItemId;
    
    private String inventoryType;
    
    public Builder id(Integer id) {
      this.id = id;
      return this;
    }
    
    public Builder name(String name) {
      this.name = name;
      return this;
    }
    
    public Builder state(String state) {
      this.state = state;
      return this;
    }
    
    public Builder advertiserId(Integer advertiserId) {
      this.advertiserId = advertiserId;
      return this;
    }
    
    public Builder lineItemId(Integer lineItemId) {
      this.lineItemId = lineItemId;
      return this;
    }
    
    public Builder inventoryType(String inventoryType) {
      this.inventoryType = inventoryType;
      return this;
    }
    
    public Campaign build() {
      Campaign advertiser = new Campaign();
      advertiser.id = this.id;
      advertiser.name = this.name;
      advertiser.state = this.state;
      advertiser.advertiserId = this.advertiserId;
      advertiser.lineItemId = this.lineItemId;
      advertiser.inventoryType = this.inventoryType;      
      return advertiser;
    }

  }
  
}
