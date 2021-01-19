package com.cpms.services;

import org.springframework.http.ResponseEntity;

import com.cpms.dto.LoginRequest;
import com.cpms.dto.UserAccountDto;
import com.cpms.pojos.UserAccount;

public interface ILoginService {
	 public ResponseEntity<?> login(LoginRequest loginCredentials);
	 public ResponseEntity<?> changePassword(String headerAuth, String updatePassword, int userId);
	 public UserAccount registerUser(UserAccountDto user);
}
