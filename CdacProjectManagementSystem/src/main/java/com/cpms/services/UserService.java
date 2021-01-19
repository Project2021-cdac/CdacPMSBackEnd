package com.cpms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cpms.dto.JwtResponse;
import com.cpms.dto.LoginRequest;
import com.cpms.dto.UserAccountDto;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.LoginRepository;
import com.cpms.security.jwt.JwtUtils;

@Service
public class LoginService implements ILoginService{
	@Autowired
	private LoginRepository userRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	public UserAccount registerUser(UserAccountDto user) {
		System.out.println("Register User");
		UserAccount userAccount = new UserAccount();
		userAccount.setFirstName(user.getFirstName());
		userAccount.setLastName(user.getLastName());
		userAccount.setEmail(user.getEmail());
		userAccount.setPassword(user.getPassword());
		userAccount.setCourseName(user.getCourseName());
		userAccount.setRole(user.getRole());
		userAccount.setDateOfBirth(user.getDateOfBirth());
		userAccount.setPhoneNumber(user.getPhoneNumber());
		UserAccount addedUser = userRepo.save(userAccount);
		System.out.println("Register User Done");

//	     String token = generateVerificationToken(user);
//	     mailService.sendMail(new NotificationEmail("Please Activate your Account",
//	                user.getEmail(), "Thank you for signing up to CDAC Acts, " +
//	                "please click on the below url to activate your account : " +
//	                "http://localhost:8080/api/auth/accountVerification/" + token));

		return addedUser;

	}

	public ResponseEntity<?> login(LoginRequest loginRequest) {
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch(BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email or Password Invalid");
		}
		System.out.println("------1---------------");
		//final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailService.loadUserByUsername(loginCredentials.getEmail());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String jwt = jwtUtils.generateJwtToken(authentication);
		//System.out.println("JWT Token: " + jwt);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		//System.out.println("------2---------------");
		//System.out.println(userDetails);
		//userRepo.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JwtResponse(jwt, userDetails.getUserAccount()));
	}

	public ResponseEntity<?> changePassword(String headerAuth, String updatePassword, int userId){
		if (!(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))) {
			return ResponseEntity.status(HttpStatus.OK).body("Already Logged Out");
		}	
		String jwt = headerAuth.substring(7, headerAuth.length());
		UserAccount user = null;
		if(isLoggedIn()) {
			user =  userRepo.changePassword(encoder.encode(updatePassword), userId); 
			jwt = refreshToken(jwt);
			if(user != null)
				return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(jwt, user));
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password Updation Failed");  
		}else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password Updation Failed");      
	}

	public String refreshToken(String authToken) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!jwtUtils.validateJwtToken(authToken)){
			return jwtUtils.generateJwtToken(authentication);
		}
		return authToken;
	}

	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}
	
}
