package com.expatrio.usermanagement.utils;

import java.util.HashSet;
import java.util.Set;

import com.expatrio.usermanagement.core.constant.URole;
import com.expatrio.usermanagement.dto.UserDto;
import com.expatrio.usermanagement.entity.Role;
import com.expatrio.usermanagement.entity.User;

public final class DataPrepareUtils {

	private DataPrepareUtils() {}

	public static UserDto prepareUserRequestDto() {
		UserDto user = new UserDto();
		user.setEmail("sp878@gmail.com");
		user.setFirstName("Santosh");
		user.setLastName("Pandey");
		user.setUserName("santosh878");
		user.setPassword("santosh878");
		user.setPhone("9878285763");
		return user;
	}

	public static User prepareUserData() {
		User user = new User();
		user.setEmail("sp878@gmail.com");
		user.setFirstName("Santosh");
		user.setLastName("Pandey");
		user.setUserName("santosh878");
		user.setPassword("santosh878");
		user.setPhone("9878285763");
		return user;
	}

	public static User prepareUserResponse() {
		User user = new User();
		user.setId(2L);
		user.setEmail("sp878@gmail.com");
		user.setFirstName("Santosh");
		user.setLastName("Pandey");
		user.setUserName("santosh878");
		user.setPassword("$2y$12$UKEEQXFRS3/iwmSHwopSo.D.5.D0f.ixidkcgKQFZdL3JtWD4dEYK");
		user.setPhone("9878285763");

		Role role = new Role();
		role.setId(1);
		role.setName(URole.ROLE_CUSTOMER);
		role.setDescription("customer role");

		Set<Role> roles = new HashSet<>();
		roles.add(role);

		user.setRoles(roles);
		return user;
	}

}
