package com.cpms.dto;

import java.time.LocalDate;

import com.cpms.pojos.Course;
import com.cpms.pojos.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {
	
	private Role role;

	private Course courseName;
	
	private String firstName;
	
	private String lastName;
	
	private String email;			
	
	private String password;
	
	private String phoneNumber;

	private LocalDate dateOfBirth;

}

