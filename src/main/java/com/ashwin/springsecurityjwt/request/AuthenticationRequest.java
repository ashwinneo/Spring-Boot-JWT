package com.ashwin.springsecurityjwt.request;

public class AuthenticationRequest {

	private String username;
	private String password;
	private String jwt;
	private boolean authAgain;
	
	public boolean isAuthAgain() {
		return authAgain;
	}

	public void setAuthAgain(boolean authAgain) {
		this.authAgain = authAgain;
	}

	public AuthenticationRequest() {
		
	}
	
	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public AuthenticationRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public AuthenticationRequest(String username, String password, String jwt) {
		this.username = username;
		this.password = password;
		this.jwt = jwt;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
    public String toString() {
        return "Request{" +
                "userName='" + username + '\'' +
                ", password=" + password +
                ", jwt=" + jwt +
                ", authAgain=" + authAgain +
                '}';
    }
	 
}
