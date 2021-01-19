package com.cpms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.LoginRequest;
import com.cpms.dto.UserAccountDto;
import com.cpms.pojos.UserAccount;
import com.cpms.services.ILoginService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3000)
@RequestMapping("/user")
public class LoginController {

	@Autowired
	private ILoginService userService;
	
	@PostMapping("/register")
	 private ResponseEntity<?> registerUser(@RequestBody UserAccountDto user) {
		 
		 //Need to receive token from frontend for specific user??????????????????????
		 //jwtUtils.validateJwtToken(/*authToken*/);
		 
		 	System.out.println("Register User");
		 	UserAccount addedUser = userService.registerUser(user);
		 	System.out.println("Register User Done");
		 	if(addedUser != null)
	        	return ResponseEntity.status(HttpStatus.OK).body(addedUser);
		 	else	
		 		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Registration encountered an error!!!");
	    }
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
	
	 @PutMapping("/changepassword")
	 public ResponseEntity<?> changePassword(@RequestHeader (value = "Authorization") String headerAuth, 
			 @RequestParam("password") String updatePassword, @RequestParam("userId") int userId) {
		 return userService.changePassword(headerAuth, updatePassword, userId);  
	 }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader (value = "Authorization") String headerAuth) {
    	if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
    		return ResponseEntity.status(HttpStatus.OK).body("Logged Out");  			//Redirect to /login page
		}
    	return ResponseEntity.status(HttpStatus.OK).body("Already Logged Out!!!");
    }
}
