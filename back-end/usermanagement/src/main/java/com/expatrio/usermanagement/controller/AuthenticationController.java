package com.expatrio.usermanagement.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expatrio.usermanagement.dto.AuthTokenDto;
import com.expatrio.usermanagement.dto.LoginUserDto;
import com.expatrio.usermanagement.security.TokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *Authentication Controller responsible to handle client requests to login and generate token
 * DTO is used to interact with client.
 */
@RestController
@RequestMapping("/api/auth")
@Api(value = "Authentication Controller responsible to handle client requests to login and generate token.")
public class AuthenticationController {

	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private TokenProvider jwtTokenUtil;

	/**
	 * API to login user and return token
	 * @param loginUser
	 * @return
	 */
	@PostMapping("login")
	@ApiOperation(value = "API to login user and generate token")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserDto loginUser) {
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginUser.getUserName(),
						loginUser.getPassword()
						)
				);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();		
		Set<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toSet());

		AuthTokenDto authTokenDto = new AuthTokenDto();
		authTokenDto.setAccessToken(token);
		authTokenDto.setUserName(userDetails.getUsername());
		authTokenDto.setRoles(roles);

		return ResponseEntity.ok(authTokenDto);
	}

}