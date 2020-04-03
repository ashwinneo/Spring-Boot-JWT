package com.ashwin.springsecurityjwt.response;

public class UpdatePasswordResponse {
	
	private int id;
	private String responseMessage;
	
	
	public UpdatePasswordResponse() {
		
	}
	
	
	public UpdatePasswordResponse(int id, String responseMessage) {
		super();
		this.id = id;
		this.responseMessage = responseMessage;
	}
	public int getId() {
		return id;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String toString() {
        return "Response{" +
                "id='" + id + '\'' +
                ", responseMessage=" + responseMessage +
                '}';
    }
	
}
