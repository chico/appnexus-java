package appnexus;

import java.io.Serializable;

public class AccountDetails implements Serializable {

  private static final long serialVersionUID = -1513521078617354480L;

  private String username;
  
  private String password;
  
  private String accessToken;
  
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
  
  public static class Builder {

    private String username;
    
    private String password;
    
    private String accessToken;
    
    public Builder username(String username) {
      this.username = username;
      return this;
    }
    
    public Builder password(String password) {
      this.password = password;
      return this;
    }
    
    public Builder accessToken(String accessToken) {
      this.accessToken = accessToken;
      return this;
    }
    
    public AccountDetails build() {
      AccountDetails accountDetails = new AccountDetails();
      accountDetails.username = this.username;
      accountDetails.password = this.password;
      accountDetails.accessToken = this.accessToken;
      return accountDetails;
    }

  }
  
}
