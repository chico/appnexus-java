package appnexus;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppnexusUnitTest {
  
  @Test
  public void testApiBase() {
    // Test default first
    assertEquals(Appnexus.ApiBase.PRODUCTION, Appnexus.getApiBase());
    
    Appnexus.environment = Appnexus.Environment.SANDBOX;
    assertEquals(Appnexus.ApiBase.SANDBOX, Appnexus.getApiBase());
    
    Appnexus.environment = Appnexus.Environment.PRODUCTION;
    assertEquals(Appnexus.ApiBase.PRODUCTION, Appnexus.getApiBase());    
  }

}