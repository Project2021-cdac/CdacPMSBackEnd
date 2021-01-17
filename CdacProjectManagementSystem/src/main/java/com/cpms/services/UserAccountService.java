package com.cpms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.UserAccount;
import com.cpms.repository.UserAccountRepository;

@Service
public class UserAccountService implements IUserAccountService {

	@Autowired 
	private UserAccountRepository repository;
	
	@Override
	public List<UserAccount> getStudentUserAccountforRegistration() {
		return repository.getStudentUserAccountforRegistration();
	}

	@Override
	public UserAccount registerUser(UserAccount user) {
		return repository.save(user);
	}
}
