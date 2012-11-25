package appnexus.exception;

import static java.lang.String.format;

public class AppnexusException extends RuntimeException {
  
  protected static final long serialVersionUID = 1L;
  
  public AppnexusException(String msg) {
    super(msg);
  }
  
  public AppnexusException(String msg, Throwable ex) {
    super(msg, ex);
  }
  
  public AppnexusException(String url, int statusCode, String errorResponse) {    
    super(format("[url: %s, error code: %d, response: %s]", url, statusCode, errorResponse));    
  }

}
