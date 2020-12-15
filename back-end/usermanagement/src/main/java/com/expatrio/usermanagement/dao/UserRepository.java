package com.expatrio.usermanagement.dao;

import java.util.List;

import com.expatrio.usermanagement.core.constant.URole;
import com.expatrio.usermanagement.entity.User;

/**
 * Repository to handle all database operation for user
 */
public interface UserRepository extends BaseRepository<User, Long>{

	/**
	 * Method to get all users with role CUSTOMER
	 * @param role
	 * @return
	 */
	List<User> findByRoles_Name(URole role);

	/**
	 * Method to get all users by username or email
	 * @param userName
	 * @param email
	 * @return
	 */
	List<User> findByUserNameOrEmail(String userName, String email);

	/**
	 * Method to get user by username
	 * @param username
	 * @return
	 */
	User findByUserName(String username);
}