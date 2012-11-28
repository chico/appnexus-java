package appnexus.api.response;

public class AuthResponse extends Response {

  private static final long serialVersionUID = 1L;

  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
  
}
