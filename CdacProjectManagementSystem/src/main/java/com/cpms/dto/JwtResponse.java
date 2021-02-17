package com.cpms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@SuppressWarnings("unused")
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Object user;

	public JwtResponse() {
		this.token = null;
		//this.userAccount = null;
	}
	
	public JwtResponse(String token,Object user) {
		this.token = token;
		this.user = user;
	}
	
}
