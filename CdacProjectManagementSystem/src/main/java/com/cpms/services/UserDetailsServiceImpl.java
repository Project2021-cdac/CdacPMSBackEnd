package com.cpms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.pojos.UserAccount;
import com.cpms.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepo;

	@Override
	 @Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserAccount user = userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("%s User Not Found ", email)));
		
		//System.out.println("UserDetailsServiceImpl: " + user);
		return UserDetailsImpl.build(user);
	}

}
