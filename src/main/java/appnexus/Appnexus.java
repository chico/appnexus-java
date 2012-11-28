package appnexus;

import static org.apache.commons.lang.StringUtils.isBlank;
import appnexus.api.Api;

/**
 * Appnexus is the main interface to the Appnexus API.<p>
 *
 */
public class Appnexus {
  
  public final static String VERSION = "1.0.1";
  
  public final static AccountDetails accountDetails = new AccountDetails();
  
  public final static Api api = new Api(accountDetails);
  
  public interface ApiBase {
    public static final String PRODUCTION = "https://api.appnexus.com";  
    public static final String SANDBOX = "http://sand.api.appnexus.com";
  }
  
  public enum Environment {PRODUCTION, SANDBOX};
  
  public static Environment environment = Environment.PRODUCTION;
  
  protected Appnexus() {
  }
  
  public static void initSandbox(String username, String password) {
    Appnexus.environment = Appnexus.Environment.SANDBOX;
    init(username, password);
  }
  
  public static void init(String username, String password) {
    if (isBlank(username) || isBlank(password)) {
      throw new IllegalArgumentException("Please set the appnexus username and password");
    }
    Appnexus.accountDetails.setUsername(username);
    Appnexus.accountDetails.setPassword(password);
    api.auth();
  }
  
  public static String getApiBase() {
    return (Environment.SANDBOX.equals(environment)) ? ApiBase.SANDBOX : ApiBase.PRODUCTION;
  }
  
}
