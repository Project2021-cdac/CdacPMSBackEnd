package com.cpms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cpms.pojos.UserAccount;
import com.cpms.repository.UserAccountRepository;

public class UserAccountService implements IUserAccountService {

	@Autowired 
	private UserAccountRepository repository;
	
	@Override
	public List<UserAccount> getStudentUserAccountforRegisteration() {
		return repository.getStudentUserAccountforRegisteration();
	}

	@Override
	public UserAccount registerUser(UserAccount user) {
		return repository.save(user);
	}
}
