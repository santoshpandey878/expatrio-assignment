package com.expatrio.usermanagement.service;

import java.util.List;

import com.expatrio.usermanagement.core.constant.URole;
import com.expatrio.usermanagement.entity.User;

public interface UserService {

	/**
	 * Method to save user details
	 * @param user
	 * @return
	 */
	User saveUser(User user);

	/**
	 * Method to get user detail by id
	 * @param userId
	 * @return
	 */
	User getUserById(Long userId);

	/**
	 * Method to get user with role customer
	 * @param userId
	 * @return
	 */
	User getUserWithRoleCustomer(Long userId);

	/**
	 * Method to get all users with role provided
	 * @param role
	 * @return
	 */
	List<User> getAllUsersWithRole(URole role);

	/**
	 * Method to update user
	 * @param userId
	 * @param user
	 * @return
	 */
	User updateUser(Long userId, User user);

	/**
	 * Method to delete a user
	 * @param userId
	 * @return 
	 */
	boolean deleteUser(Long userId);

	/**
	 * Method to get users by userName or email
	 * @param userName
	 * @param email
	 * @return
	 */
	List<User> getUsersByUsernameOrEmail(String userName, String email);
}
