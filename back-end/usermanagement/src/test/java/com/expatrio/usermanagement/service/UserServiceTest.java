package com.expatrio.usermanagement.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.expatrio.usermanagement.core.constant.URole;
import com.expatrio.usermanagement.core.exception.ResourceNotFoundException;
import com.expatrio.usermanagement.core.utils.MessageUtil;
import com.expatrio.usermanagement.dao.RoleRepository;
import com.expatrio.usermanagement.dao.UserRepository;
import com.expatrio.usermanagement.entity.Role;
import com.expatrio.usermanagement.entity.User;
import com.expatrio.usermanagement.service.impl.UserServiceImpl;
import com.expatrio.usermanagement.utils.DataPrepareUtils;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Mock private UserRepository userRepository;
	@Mock private MessageUtil message;
	@Mock private RoleRepository roleRepository;
	@Mock private BCryptPasswordEncoder bcryptEncoder;
	@InjectMocks private UserServiceImpl userService;

	private User userResponse;

	@Before
	public void init() {
		userResponse = DataPrepareUtils.prepareUserResponse();
	}

	@Test
	public void testGetUserById_ShouldReturnUser() {
		Mockito.when(userRepository.findById(Mockito.anyLong()))
		.thenReturn(Optional.of(userResponse));

		User user = userService.getUserById(2L);

		Assert.assertEquals(2, user.getId().longValue());
		Assert.assertEquals(userResponse.getEmail(), user.getEmail());
		Assert.assertEquals(userResponse.getUserName(), user.getUserName());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetUserById_ShouldThrowException() {
		Mockito.when(userRepository.findById(Mockito.anyLong()))
		.thenThrow(ResourceNotFoundException.class);

		userService.getUserById(2L);
	}

	@Test
	public void testGetUserWithRoleCustomer_ShouldReturnUser() {
		Mockito.when(userRepository.findById(Mockito.anyLong()))
		.thenReturn(Optional.of(userResponse));

		User user = userService.getUserWithRoleCustomer(2L);

		Assert.assertEquals(userResponse.getEmail(), user.getEmail());
		Assert.assertEquals(userResponse.getUserName(), user.getUserName());
		Assert.assertEquals(userResponse.getRoles().size(), user.getRoles().size());
		Assert.assertEquals(userResponse.getPassword(), user.getPassword());
	}

	@Test
	public void testGetAllUsersWithRole_ShouldReturnUsers() {
		Mockito.when(userRepository.findByRoles_Name(Mockito.any(URole.class)))
		.thenReturn(Arrays.asList(userResponse));

		List<User> user = userService.getAllUsersWithRole(URole.ROLE_CUSTOMER);

		Assert.assertEquals(1, user.size());
	}

	@Test
	public void testSaveUser_ShouldReturnSavedUser() {
		Mockito.when(userRepository.save(Mockito.any(User.class)))
		.thenReturn(userResponse);

		Mockito.when(bcryptEncoder.encode(Mockito.anyString()))
		.thenReturn("$2y$12$UKEEQXFRS3/iwmSHwopSo.D.5.D0f.ixidkcgKQFZdL3JtWD4dEYK");

		Role role = new Role();
		role.setId(1);;
		role.setName(URole.ROLE_CUSTOMER);

		Mockito.when(roleRepository.findByName(Mockito.any(URole.class)))
		.thenReturn(role);

		User user = userService.saveUser(DataPrepareUtils.prepareUserData());

		Assert.assertEquals(2, user.getId().longValue());
		Assert.assertEquals("$2y$12$UKEEQXFRS3/iwmSHwopSo.D.5.D0f.ixidkcgKQFZdL3JtWD4dEYK", user.getPassword());
	}

	@Test
	public void testUpdateUser_ShouldReturnUpdatedUser() {
		Mockito.when(userRepository.findById(Mockito.anyLong()))
		.thenReturn(Optional.of(userResponse));

		Mockito.when(bcryptEncoder.encode(Mockito.anyString()))
		.thenReturn("$2y$12$UKEEQXFRS3/iwmSHwopSo.D.5.D0f.ixidkcgKQFZdL3JtWD4dEYK");

		User updatedResponse = DataPrepareUtils.prepareUserResponse();
		updatedResponse.setFirstName("Sam");
		updatedResponse.setLastName("Due");

		Mockito.when(userRepository.save(Mockito.any(User.class)))
		.thenReturn(updatedResponse);

		User user = new User();
		user.setEmail("sp878@gmail.com");
		user.setFirstName("Sam");
		user.setLastName("Due");
		user.setUserName("santosh878");
		user.setPassword("santosh878");

		User updatedUser = userService.updateUser(2L, user);

		Assert.assertEquals("Sam", updatedUser.getFirstName());
		Assert.assertEquals("Due", updatedUser.getLastName());
		Assert.assertEquals("$2y$12$UKEEQXFRS3/iwmSHwopSo.D.5.D0f.ixidkcgKQFZdL3JtWD4dEYK", updatedUser.getPassword());
	}

	@Test
	public void testDeleteUser_ShouldReturnTrue() {
		Mockito.when(userRepository.findById(Mockito.anyLong()))
		.thenReturn(Optional.of(userResponse));

		Mockito.doNothing().when(userRepository).delete(Mockito.any(User.class));

		Assert.assertTrue(userService.deleteUser(2L));
	}


}