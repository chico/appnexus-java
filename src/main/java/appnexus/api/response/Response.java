package appnexus.api.response;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class Response implements Serializable {

  private static final String NOT_AVAILABLE = "N/A";

  private static final long serialVersionUID = 1L;

  private String status;
  
  private String error;
  
  private String errorId;
  
  public String getStatus() {
    return StringUtils.isNotBlank(status) ? status : NOT_AVAILABLE;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getError() {
    return StringUtils.isNotBlank(error) ? error : NOT_AVAILABLE;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getErrorId() {
    return StringUtils.isNotBlank(errorId) ? errorId : NOT_AVAILABLE;
  }

  public void setErrorId(String errorId) {
    this.errorId = errorId;
  }

}
