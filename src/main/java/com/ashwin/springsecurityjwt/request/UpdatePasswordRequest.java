package com.ashwin.springsecurityjwt.request;

public class UpdatePasswordRequest {

	private String id;
	private String oldPassword;
	private String newPassword;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
