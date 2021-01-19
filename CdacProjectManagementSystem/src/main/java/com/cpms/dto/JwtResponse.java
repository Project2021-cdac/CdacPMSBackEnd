package com.cpms.dto;

import com.cpms.pojos.UserAccount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@SuppressWarnings("unused")
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private UserAccount userAccount;

	public JwtResponse() {
		this.token = null;
		this.userAccount = null;
	}
	
	public JwtResponse(String token, UserAccount userAccount) {
		this.token = token;
		this.userAccount = userAccount;
	}

}
