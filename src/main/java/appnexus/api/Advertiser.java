package appnexus.api;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Advertiser implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;
  
  private String name;
  
  private String state;
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  @Override
  public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
  }
  
  public static class Builder {

    private String id;
    
    private String name;
    
    private String state;
    
    public Builder id(String id) {
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
