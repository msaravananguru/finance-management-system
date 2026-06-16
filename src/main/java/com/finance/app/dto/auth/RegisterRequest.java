package com.finance.app.dto.auth;

public class RegisterRequest {

	private String username;
	
	private String fullName;

	private String email;

	private String password;

	private String requestedRole;
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRequestedRole() {
		return requestedRole;
	}

	public void setRequestedRole(String requestedRole) {
		this.requestedRole = requestedRole;
	}
	
	
	public String getFullName() {
	    return fullName;
	}

	public void setFullName(
	        String fullName) {
	    this.fullName = fullName;
	}
	
}