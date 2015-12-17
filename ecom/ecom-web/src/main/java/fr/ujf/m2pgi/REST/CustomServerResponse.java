package fr.ujf.m2pgi.REST;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * In order to fit 'application/json' request, sometimes we give feedback to the front end thanks to this custom response.
 * It will send a response formated as a json and an internal error status code.
 */
@XmlRootElement
public class CustomServerResponse {
	
  public CustomServerResponse(boolean success, String message, Object data) {
	  this.success = success;
	  this.message = message;
	  this.data = data;
  }
  
  public CustomServerResponse(boolean success, String message) {
	  this.success = success;
	  this.message = message;
	  this.data = null;
  }

  @XmlElement
  private boolean success;

  @XmlElement
  private String message;

  @XmlElement
  private Object data;

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public boolean getSuccess() {
    return success;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public Object getData() {
    return data;
  }

}
