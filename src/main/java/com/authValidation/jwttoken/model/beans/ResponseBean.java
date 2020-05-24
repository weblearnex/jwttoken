package com.authValidation.jwttoken.model.beans;



public class ResponseBean<T>  {
	private String message;
	private int statuscode;
	private T responseBody;
	
	public ResponseBean() {
		
	}
	
	public ResponseBean(String message, int statuscode, T responseBody) {
		super();
		this.message = message;
		this.statuscode = statuscode;
		this.responseBody = responseBody;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}
	public T getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	} 
	
	
	
	

} 
	


