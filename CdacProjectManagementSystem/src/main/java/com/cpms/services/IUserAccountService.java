package com.cpms.services;

import java.util.List;

import com.cpms.pojos.UserAccount;

public interface IUserAccountService {
	List<UserAccount> getStudentUserAccountforRegistration();
	UserAccount registerUser(UserAccount user);
	
}