package com.cpms.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.JwtResponse;
import com.cpms.dto.LoginRequest;
import com.cpms.dto.ResponseMessage;
import com.cpms.security.jwt.JwtUtils;
import com.cpms.services.IUserService;
import com.cpms.services.UserDetailsImpl;
import com.cpms.services.UserDetailsServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
@Api(value = "Login Resource")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private IUserService userService;

	@ApiOperation(value = "Authenticate and Login User", authorizations = { @Authorization(value="jwtToken") })
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "Successful Login and sent Jwt token + User Info"),
					@ApiResponse(code = 401, message = "Invalid Credentials")
			}
			)
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {

		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch(BadCredentialsException ex) {
			throw new Exception("INVALID_CREDENTIALS", ex);
			//return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email or Password Invalid");
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

		//final String jwt = jwtUtils.generateToken(userDetails);
		final String jwt = jwtUtils.generateToken(authentication);
		UserDetailsImpl user = (UserDetailsImpl) userDetails;		
		System.out.println("User Verification before Sending to Frontend:::: " + user.getUser());
		return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(jwt, user.getUser()));
	}


	//TODO Work in progress ---> Kept on hold for testing
	@PostMapping("/changepassword")
	public ResponseEntity<?> changePassword(HttpServletRequest request, @RequestBody LoginRequest loginRequest) {
		if(isLoggedIn()) {
			String token = parseJwt(request);
			boolean status = false;
			if(token == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Login Again For Safety Reasons!!!"));

			if(jwtUtils.extractEmail(token).equals(loginRequest.getEmail()))
				status = userService.changePassword(loginRequest.getPassword(), loginRequest.getEmail());  
			if(status)
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Password Updation Successfull"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Password Updation Failed"));
	}

	//Can be used by any api to verify if user is logged in or not
	private boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}

	private String parseJwt(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
			return authorizationHeader.substring(7);
		}

		return null;
	}
	//	@PostMapping("/logout")
	//	public ResponseEntity<?> logout(@RequestHeader (value = "Authorization") String headerAuth) {
	//		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
	//			return ResponseEntity.status(HttpStatus.OK).body("Logged Out");  			//Redirect to /login page
	//		}
	//		return ResponseEntity.status(HttpStatus.OK).body("Already Logged Out!!!");
	//	}
}
