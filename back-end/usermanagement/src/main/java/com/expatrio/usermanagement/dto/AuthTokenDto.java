package com.expatrio.usermanagement.dto;

import java.util.Set;

import io.swagger.annotations.ApiModel;

@ApiModel(description="Model for token information")
public class AuthTokenDto {

	private String userName;
	private String accessToken;
	private Set<String> roles;
	
	public AuthTokenDto() {}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}