package appnexus.api;

import java.io.Serializable;

public class Response implements Serializable {

  private static final long serialVersionUID = 1L;

  private String status;
  
  private String error;
  
  private String errorId;
  
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getErrorId() {
    return errorId;
  }

  public void setErrorId(String errorId) {
    this.errorId = errorId;
  }

}
