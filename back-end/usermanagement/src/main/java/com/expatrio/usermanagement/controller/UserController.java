package com.expatrio.usermanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expatrio.usermanagement.core.constant.URole;
import com.expatrio.usermanagement.core.utils.DtoConverter;
import com.expatrio.usermanagement.dto.UserDto;
import com.expatrio.usermanagement.entity.User;
import com.expatrio.usermanagement.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *User controller class responsible to handle client requests to create,update and list users.
 * DTO is used to interact with client.
 */
@RestController
@RequestMapping("/api/users")
@Api(value = "User controller class responsible to handle client requests to create,update and list users.")
public class UserController {

	@Autowired private UserService userService;
	@Autowired private DtoConverter dtoConverter;

	/**
	 * API to get a user with role CUSTOMER by ADMIN
	 * Accessible only for ADMIN role
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("{userId}")
	@ApiOperation(value = "API to get a user with role CUSTOMER by ADMIN")
	public UserDto getUserWithRoleCustomer(@PathVariable Long userId) {
		User user = userService.getUserWithRoleCustomer(userId);
		return dtoConverter.convert(user, UserDto.class);
	}

	/**
	 * API to list all users with role CUSTOMER by ADMIN
	 * Accessible only for ADMIN role
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	@ApiOperation(value = "API to list all users by ADMIN")
	public List<UserDto> getAllUsersWithRoleCustomer() {
		List<User> users = userService.getAllUsersWithRole(URole.ROLE_CUSTOMER);
		return users.stream()
				.map(user -> dtoConverter.convert(user, UserDto.class))
				.collect(Collectors.toList());
	}

	/**
	 * API to create user with role CUSTOMER by ADMIN
	 * @param userDto
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ApiOperation(value = "API to create user with role CUSTOMER by ADMIN")
	public UserDto saveUser(@Valid @RequestBody UserDto userDto) {
		User user = userService.saveUser(dtoConverter.convert(userDto, User.class));
		return dtoConverter.convert(user, UserDto.class);
	}

	/**
	 * API to update user with role CUSTOMER by ADMIN
	 * @param userDto
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{userId}")
	@ApiOperation(value = "API to update user with role CUSTOMER by ADMIN")
	public UserDto updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
		User user = userService.updateUser(userId, dtoConverter.convert(userDto, User.class));
		return dtoConverter.convert(user, UserDto.class);
	}

	/**
	 * API to delete user with role CUSTOMER by ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{userId}")
	@ApiOperation(value = "API to delete user with role CUSTOMER by ADMIN")
	public void deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
	}

}