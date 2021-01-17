package com.cpms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.UserAccount;
import com.cpms.repository.UserAccountRepository;

@Service
public class UserAccountService implements IUserAccountService {

	@Autowired 
	private UserAccountRepository userAcctRepository;

	@Override
	public UserAccount registerUser(UserAccount user) {
		return userAcctRepository.save(user);
	}
}
