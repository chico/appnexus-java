package appnexus.api;

import static java.lang.String.format;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Advertiser implements Serializable {

  private static final long serialVersionUID = 1L;
  
  protected static final String ADD_ADVERTISER_JSON_PAYLOAD = "{\"advertiser\":{\"name\":\"%s\", \"state\":\"%s\"}}";
  
  public interface State {
    public final static String ACTIVE = "active";
    public final static String INACTIVE = "inactive";
  }

  private Integer id;
  
  private String name;
  
  private String state;
  
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
  
  public String getAddAdvertiserJsonPayload() {
    // TODO use gson
    return format(ADD_ADVERTISER_JSON_PAYLOAD, this.getName(), this.getState());
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
    
    public Advertiser build() {
      Advertiser advertiser = new Advertiser();
      advertiser.id = this.id;
      advertiser.name = this.name;
      advertiser.state = this.state;
      return advertiser;
    }

  }
  
}
