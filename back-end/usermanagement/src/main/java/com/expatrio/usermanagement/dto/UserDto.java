package com.expatrio.usermanagement.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

@ApiModel(description="All details about the User.")
public class UserDto {

	private Long id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;

	public UserDto() {}

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

	@NotBlank(message = "First name cannot be empty")
	@Size(min = 3, message = "First name must be at least 3 characters")
	@Size(max = 50, message = "First name cannot be greater than 50 charecters")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@NotBlank(message = "Last name cannot be empty")
	@Size(min = 3, message = "Last name must be at least 3 characters")
	@Size(max = 50, message = "Last name cannot be greater than 50 charecters")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@NotEmpty(message = "Email cannot be empty")
	@Size(max = 100, message = "Email cannot be greater than 100 charecters")
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
