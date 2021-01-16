package com.cpms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.dto.LoginRequest;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.LoginRepository;

@Service
public class LoginServiceImpl implements ILoginService{
	@Autowired
	private LoginRepository loginRepo;

	@Override
	public UserAccount login(LoginRequest loginCredentials) {
		//System.out.println("In Login Service");
		return loginRepo.findByEmailAndPassword(loginCredentials.getEmail(), loginCredentials.getPassword());
	}

	@Override
	public UserAccount changePassword( String updatePassword, int userId) {
		return loginRepo.changePassword(updatePassword, userId);
	}

}
