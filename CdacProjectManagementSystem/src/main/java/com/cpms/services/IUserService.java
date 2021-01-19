package com.cpms.services;

import com.cpms.pojos.UserAccount;

public interface IUserService {
	 public UserAccount changePassword(String headerAuth, String updatePassword, int userId);
//	 public UserAccount registerUser(UserAccountDto user);
}
