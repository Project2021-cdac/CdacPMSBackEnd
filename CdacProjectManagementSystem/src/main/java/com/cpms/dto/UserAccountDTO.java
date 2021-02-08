package com.cpms.dto;

import java.time.LocalDate;

import com.cpms.pojos.Course;
import com.cpms.pojos.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAccountDTO {
	
	private String firstName;
	
	private String lastName;
	
	private String email;			
	
	private String password;
	
	private Course courseName;
	
	private Role role;

	private LocalDate dateOfBirth;
	
	private String phoneNumber;

	public UserAccountDTO(String firstName, String lastName, String email, String password, Course courseName,
			Role role, LocalDate dateOfBirth, String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.courseName = courseName;
		this.role = role;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
	}


}

