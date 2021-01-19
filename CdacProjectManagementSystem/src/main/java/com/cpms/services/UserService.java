package com.cpms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cpms.pojos.UserAccount;
import com.cpms.repository.LoginRepository;

@Service
public class UserService implements IUserService{
	@Autowired
	private LoginRepository userRepo;

	@Autowired
	private PasswordEncoder encoder;


//	public UserAccount registerUser(UserAccountDto user) {
//		System.out.println("Register User");
//		UserAccount userAccount = new UserAccount();
//		userAccount.setFirstName(user.getFirstName());
//		userAccount.setLastName(user.getLastName());
//		userAccount.setEmail(user.getEmail());
//		userAccount.setPassword(user.getPassword());
//		userAccount.setCourseName(user.getCourseName());
//		userAccount.setRole(user.getRole());
//		userAccount.setDateOfBirth(user.getDateOfBirth());
//		userAccount.setPhoneNumber(user.getPhoneNumber());
//		UserAccount addedUser = userRepo.save(userAccount);
//		System.out.println("Register User Done");
//
////	     String token = generateVerificationToken(user);
////	     mailService.sendMail(new NotificationEmail("Please Activate your Account",
////	                user.getEmail(), "Thank you for signing up to CDAC Acts, " +
////	                "please click on the below url to activate your account : " +
////	                "http://localhost:8080/api/auth/accountVerification/" + token));
//
//		return addedUser;
//
//	}

	public UserAccount changePassword(String headerAuth, String updatePassword, int userId){
		UserAccount user = null;
		if(isLoggedIn()) {
			user =  userRepo.changePassword(encoder.encode(updatePassword), userId); 
			return user;
		}
		return null;
	}

	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}
	
}
