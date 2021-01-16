package com.cpms.services;

import com.cpms.dto.LoginRequest;
import com.cpms.pojos.UserAccount;

public interface ILoginService {
	 public UserAccount login(LoginRequest loginCredentials);
	 public UserAccount changePassword(String updatePassword, int userId);
}
