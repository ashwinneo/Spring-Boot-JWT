package com.ashwin.springsecurityjwt.response;

public class UpdatePasswordResponse {
	
	private String id;
	private String responseMessage;
	public String getId() {
		return id;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String toString() {
        return "Response{" +
                "id='" + id + '\'' +
                ", responseMessage=" + responseMessage +
                '}';
    }
	
}
