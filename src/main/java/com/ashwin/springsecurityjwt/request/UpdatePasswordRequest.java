package com.ashwin.springsecurityjwt.request;

public class UpdatePasswordRequest {

	public int id;
	public String oldPassword;
	public String newPassword;
	
	
	public UpdatePasswordRequest() {
		
	}
	
	public UpdatePasswordRequest(int id, String oldPassword, String newPassword) {
		this.id = id;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", oldPassword=" + oldPassword +
                ", newPassword=" + newPassword +
                '}';
    }
	
}
