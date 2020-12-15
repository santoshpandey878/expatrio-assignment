package com.expatrio.usermanagement;

import static io.restassured.RestAssured.given;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.expatrio.usermanagement.core.constant.ApplicationConstant;
import com.expatrio.usermanagement.dto.AuthTokenDto;
import com.expatrio.usermanagement.dto.LoginUserDto;
import com.expatrio.usermanagement.dto.UserDto;
import com.expatrio.usermanagement.utils.DataPrepareUtils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsermanagementApplicationTests extends AbstractIntegrationTest{

	private RequestSpecification specification;
	private String authAPIBasePath = "/usermanagement/api/auth/login";
	private String userAPIBasePath = "/usermanagement/api/users";
	private Long userId;

	private RequestSpecification getRequestSpecification() {
		if(null == specification) {
			LoginUserDto user = new LoginUserDto();
			user.setUserName("admin");
			user.setPassword("admin");

			AuthTokenDto authTokenDto =
					given()
					.basePath(authAPIBasePath)
					.port(portNumber)
					.contentType("application/json")
					.body(user)
					.when()
					.post()
					.then()
					.statusCode(200)
					.extract().as(AuthTokenDto.class);


			specification =
					new RequestSpecBuilder()
					.addHeader(ApplicationConstant.HEADER_STRING, "Bearer "+authTokenDto.getAccessToken())
					.setPort(portNumber)
					.build();
		}

		return specification;
	}

	@Before
	public void createUserWithRoleCustomer() {
		UserDto response = given()
				.spec(getRequestSpecification().basePath(userAPIBasePath))
				.contentType("application/json")
				.body(DataPrepareUtils.prepareUserRequestDto())
				.when()
				.post()
				.then()
				.extract()
				.as(UserDto.class);
		userId = response.getId();
	}

	@After
	public void deleteUserWithRoleCustomer() {
		given()
		.spec(getRequestSpecification().basePath(userAPIBasePath+"/"+userId))
		.when()
		.delete();
	}

	@Test
	public void testGetUserWithRoleCustomer_ShouldReturnUser() {
		UserDto userDto =
				given()
				.spec(getRequestSpecification().basePath(userAPIBasePath+"/"+userId))
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.as(UserDto.class);

		Assert.assertEquals("sp878@gmail.com", userDto.getEmail());
		Assert.assertEquals("santosh878", userDto.getUserName());
		Assert.assertEquals("9878285763", userDto.getPhone());
		Assert.assertEquals("Santosh", userDto.getFirstName());
		Assert.assertEquals("Pandey", userDto.getLastName());

	}

	@Test
	public void testGetAllUsersWithRoleCustomer_ShouldReturnUser() {
		UserDto[] userDto = given()
				.spec(getRequestSpecification().basePath(userAPIBasePath))
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.as(UserDto[].class);

		Assert.assertEquals(1, userDto.length);
	}

	@Test
	public void testUpdateUserWithRoleCustomer_ShouldReturnUser() {
		UserDto requestDto = DataPrepareUtils.prepareUserRequestDto();
		requestDto.setFirstName("Vikash");

		UserDto userDto = given()
				.spec(getRequestSpecification().basePath(userAPIBasePath+"/"+userId))
				.contentType("application/json")
				.body(requestDto)
				.when()
				.put()
				.then()
				.statusCode(200)
				.extract()
				.as(UserDto.class);

		Assert.assertEquals("Vikash", userDto.getFirstName());
	}

}

