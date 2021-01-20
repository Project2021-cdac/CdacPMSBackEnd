package com.cpms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.JwtResponse;
import com.cpms.dto.LoginRequest;
import com.cpms.pojos.UserAccount;
import com.cpms.security.jwt.JwtUtils;
import com.cpms.services.IUserService;
import com.cpms.services.UserDetailsImpl;
import com.cpms.services.UserDetailsServiceImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3000)
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IUserService userService;
	
//	@PostMapping("/register")
//	 private ResponseEntity<?> registerUser(@RequestBody UserAccountDto user) {
//		 
//		 //TODO Need to receive token from frontend for specific user??????????????????????
//		 //jwtUtils.validateJwtToken(/*authToken*/);
//		 
//		 	System.out.println("Register User");
//		 	UserAccount addedUser = userService.registerUser(user);
//		 	System.out.println("Register User Done");
//		 	if(addedUser != null)
//	        	return ResponseEntity.status(HttpStatus.OK).body(addedUser);
//		 	else	
//		 		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Registration encountered an error!!!");
//	    }
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch(BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email or Password Invalid");
		}


		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String jwt = jwtUtils.generateToken(userDetails);

		UserDetailsImpl user = (UserDetailsImpl) userDetails;		
		return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(jwt, user.getUserAccount()));
    }
	
	
	//TODO Work in progress ---> Keep on hold for testing
	 @PutMapping("/changepassword")
	 public ResponseEntity<?> changePassword(@RequestHeader (value = "Authorization") String headerAuth, 
			 @RequestParam("password") String updatePassword, @RequestParam("userId") int userId) {
		 UserAccount user =  userService.changePassword(headerAuth, updatePassword, userId);  
		 if(user != null)
				return ResponseEntity.status(HttpStatus.OK).body(user);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password Updation Failed");  
	 }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader (value = "Authorization") String headerAuth) {
    	if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
    		return ResponseEntity.status(HttpStatus.OK).body("Logged Out");  			//Redirect to /login page
		}
    	return ResponseEntity.status(HttpStatus.OK).body("Already Logged Out!!!");
    }
}
