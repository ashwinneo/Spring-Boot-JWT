package com.ashwin.springsecurityjwt.request;

public class DeactivateAccountRequest {
	private int id;
	private String password;
	private boolean active;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", password=" + password +
                ", active=" + active +
                '}';
    }
}

