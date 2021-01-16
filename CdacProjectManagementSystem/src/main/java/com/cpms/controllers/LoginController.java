package com.cpms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.LoginRequest;
import com.cpms.exception_handler.RecordNotFoundException;
import com.cpms.pojos.UserAccount;
import com.cpms.services.ILoginService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3000)
@RequestMapping("/user")
public class LoginController {
	
	@Autowired
	private ILoginService loginService;
	
	 @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequest loginCredentials) {
		 	System.out.println("In Login Controller");
		 	UserAccount verifiedUser =  loginService.login(loginCredentials); 
		 	System.out.println(verifiedUser);
	        if(verifiedUser != null) {
	        	return ResponseEntity.status(HttpStatus.OK).body(verifiedUser);
			}else {
				//return new ResponseEntity<>(new RecordNotFoundException("Email or Password Invalid"),HttpStatus.NOT_FOUND);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email or Password Invalid");
			}
	    }
	 
	 @PutMapping("/changepassword")
	 public ResponseEntity<?> changePassword(@RequestParam("password") String updatePassword, @RequestParam("userId") int userId) {
		 UserAccount verifiedUser =  loginService.changePassword(updatePassword, userId); 
		 System.out.println(verifiedUser);
	        if(verifiedUser != null) {
	        	return ResponseEntity.status(HttpStatus.OK).body(verifiedUser);
	        	//return new ResponseEntity<>(verifiedUser, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new RecordNotFoundException("Password Updation Failed"),HttpStatus.NOT_FOUND);
			}
	        
	 }
	
}
