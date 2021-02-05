package com.cpms.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cpms.pojos.UserAccount;
import com.cpms.repository.UserRepository;

@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository userRepo;

	public boolean changePassword(final String newPassword, final String email){
//		if(isLoggedIn()) {
			Optional<UserAccount> user = userRepo.findByEmail(email);
			if(user.isPresent()) {
				UserAccount userAccount = user.get();
				System.out.println(userAccount.getPassword());
				System.out.println(newPassword);
				userAccount.setPassword(newPassword);
				userRepo.save(userAccount);
				return true;
			}
		//}
		return false;
	}

	//Can be used by any api to verify if user is logged in or not
	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}

}
