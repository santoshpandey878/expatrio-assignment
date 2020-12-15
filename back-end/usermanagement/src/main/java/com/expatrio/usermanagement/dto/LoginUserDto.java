package com.expatrio.usermanagement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

@ApiModel(description="All details about the login user.")
public class LoginUserDto {

	private String userName;
	private String password;


	public LoginUserDto() {}

	@NotBlank(message = "Username cannot be empty")
	@Size(min = 3, message = "Username must be at least 3 characters")
	@Size(max = 20, message = "Username cannot be greater than 20 charecters")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@NotBlank(message = "Password cannot be empty")
	@Size(min = 5, message = "Password must be at least 5 characters")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
