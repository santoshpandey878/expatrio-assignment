package com.expatrio.usermanagement.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.expatrio.usermanagement.core.constant.MessageConstant;
import com.expatrio.usermanagement.core.constant.URole;
import com.expatrio.usermanagement.core.exception.ResourceNotFoundException;
import com.expatrio.usermanagement.core.exception.ServiceException;
import com.expatrio.usermanagement.core.utils.MessageUtil;
import com.expatrio.usermanagement.core.utils.NullUtil;
import com.expatrio.usermanagement.dao.RoleRepository;
import com.expatrio.usermanagement.dao.UserRepository;
import com.expatrio.usermanagement.entity.Role;
import com.expatrio.usermanagement.entity.User;
import com.expatrio.usermanagement.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired private UserRepository userRepository;
	@Autowired private MessageUtil message;
	@Autowired private BCryptPasswordEncoder bcryptEncoder;
	@Autowired private RoleRepository roleRepository;

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(message.get(MessageConstant.USER_NOT_FOUND)));
	}

	@Override
	public User getUserWithRoleCustomer(Long userId) {
		User user = getUserById(userId);

		//validate user role i.e.only user with role CUSTOMER can be fetched
		validateUserRole(user, MessageConstant.ADMIN_USER_CAN_NOT_FETCHED);

		return user;
	}

	@Override
	public List<User> getAllUsersWithRole(URole role) {
		return userRepository.findByRoles_Name(role);
	}

	@Transactional
	@Override
	public User saveUser(User user) {
		//validate user i.e. if user already exist with provided userName or email
		validateUser(user);
		
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		user.setRoles(getCustomerRole());
		return userRepository.save(user);
	}

	/**
	 * Method to validate user
	 * @param user
	 */
	private void validateUser(User user) {
		if(!CollectionUtils.isEmpty(getUsersByUsernameOrEmail(user.getUserName(), user.getEmail()))) {
			throw new ServiceException(message.get(MessageConstant.USER_ALREADY_EXIST, user.getUserName(), user.getEmail()));
		}
	}

	@Transactional
	@Override
	public User updateUser(Long userId, User user) {
		User oldUser = getUserById(userId);

		//validate user role i.e.only user with role CUSTOMER can be updated
		validateUserRole(oldUser, MessageConstant.ADMIN_USER_CAN_NOT_UPDATED);

		oldUser.setFirstName(user.getFirstName());
		oldUser.setLastName(user.getLastName());
		oldUser.setPhone(user.getPhone());

		// change password if updated new password provided
		if(user.getPassword().equals(oldUser.getPassword())) {
			oldUser.setPassword(oldUser.getPassword());
		} else {
			oldUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		}

		return userRepository.save(oldUser);
	}

	@Transactional
	@Override
	public boolean deleteUser(Long userId) {
		User user = getUserById(userId);

		//validate user role i.e.only user with role CUSTOMER can be deleted
		validateUserRole(user, MessageConstant.ADMIN_USER_CAN_NOT_DELETED);

		user.getRoles().removeAll(user.getRoles());
		userRepository.delete(user);
		return true;
	}

	/**
	 * Method to validate user role 
	 * i. e. user should belong to role either CUSTOMER or both CUSTOMER & ADMIN
	 * @param user
	 * @param messageTemplate
	 */
	private void validateUserRole(User user, String messageTemplate) {
		AtomicBoolean isCustomer = new AtomicBoolean(false);
		user.getRoles().forEach(role -> {
			if(role.getName().equals(URole.ROLE_CUSTOMER)) {
				isCustomer.set(true);
				return;
			}
		});

		if(!isCustomer.get()) {
			throw new ServiceException(message.get(messageTemplate));
		}
	}

	/**
	 * Method to get CUSTOMER role object
	 * @return
	 */
	private Set<Role> getCustomerRole() {
		Set<Role> roles= new HashSet<>();
		roles.add(roleRepository.findByName(URole.ROLE_CUSTOMER));
		return roles;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if(NullUtil.isNull(user))
		{
			throw new UsernameNotFoundException("Invalid username"); 
		} 

		return new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPassword(), getAuthority(user));
	}

	/**
	 * Method to get authorities of a user
	 * @param user
	 * @return
	 */
	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName().name()));
		});
		return authorities;
	}

	@Override
	public List<User> getUsersByUsernameOrEmail(String userName, String email) {
		return userRepository.findByUserNameOrEmail(userName, email);
	}

}
