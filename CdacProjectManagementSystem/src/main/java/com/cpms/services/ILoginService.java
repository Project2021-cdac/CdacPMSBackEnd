package com.cpms.services;

import com.cpms.dto.LoginRequest;
import com.cpms.pojos.UserAccount;

public interface ILoginService {
	 UserAccount login(LoginRequest loginCredentials);
	 UserAccount changePassword(String updatePassword, int userId);
}
