package com.cpms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.pojos.Role;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepo;

	@Override
	 @Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Admin admin = null;
//		Guide guide = null;
//		Student student = null;
		
		Object user = null;
		UserAccount userAccount = userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("%s User Not Found ", email)));
		
		System.out.println("UserDetailsServiceImpl: USER" + user);
		if(userAccount.getRole().equals(Role.ROLE_ADMIN)) {
			user = userRepo.findAdminByUserId(userAccount.getId());
			//user = admin;
			System.out.println("UserDetailsServiceImpl: ADMIN" + userAccount);
		}else if(userAccount.getRole().equals(Role.ROLE_GUIDE)) {
			user = userRepo.findGuideByUserId(userAccount.getId());
			//user = guide;
			System.out.println("UserDetailsServiceImpl: GUIDE" + userAccount);
		}else {
			user = userRepo.findStudentByUserId(userAccount.getId());
			//user = student;
			System.out.println("UserDetailsServiceImpl: STUDENT" + userAccount);
		}
		
		return UserDetailsImpl.build(userAccount, user);
	}

}
