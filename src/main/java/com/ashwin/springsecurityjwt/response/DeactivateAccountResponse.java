package com.ashwin.springsecurityjwt.response;

public class DeactivateAccountResponse {
	private int id;
	private boolean active;
	private String responseStatus;

	public DeactivateAccountResponse() {
		
	}
	
	
	public DeactivateAccountResponse(int id, boolean active, String responseStatus) {
		this.id = id;
		this.active = active;
		this.responseStatus = responseStatus;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	
}
