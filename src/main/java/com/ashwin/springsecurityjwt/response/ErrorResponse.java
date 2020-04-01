package com.ashwin.springsecurityjwt.response;

public class ErrorResponse {

	private String status;
	private String errorId;
	private String errorMessage;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
