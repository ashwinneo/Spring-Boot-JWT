package com.ashwin.springsecurityjwt.response;

public class SuccessResponse {

	private String successId;
	private String successMessage;
	private String jwt;
	
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getSuccessId() {
		return successId;
	}
	public void setSuccessId(String successId) {
		this.successId = successId;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
}
