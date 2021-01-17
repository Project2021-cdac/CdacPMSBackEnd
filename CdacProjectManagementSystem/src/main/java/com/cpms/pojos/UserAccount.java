package com.cpms.pojos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="user_table")
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=50)
	@Pattern(regexp = "^[a-zA-Z]{1,50}$" , message = "First name must be alphabetic and should be between 1 and 50 characters long!!!")
	private String firstName;
	
	@Column(length=50)
	@Pattern(regexp = "^[a-zA-Z]{1,50}$" , message = "Last name must be alphabetic and should be between 1 and 50 characters long!!!")
	private String lastName;
	
	@Column(length=100)
	@Email
	private String email;
	
	@Column(length=50)
	@Size(min=4 , max=16 , message="Password must be 4-16 characters long")
	private String password;
	
	@Column(length=15, unique = true)
	@Pattern(regexp = "^[0-9]{10, 15}$" , message = "Mobile number be between 10 to 15 digits!!!")
	private String phoneNumber;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	private Course courseName;
	
	
	public UserAccount(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public UserAccount(Integer id) {
		this.id=id;
	}
	
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", role=" + role + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", phoneNumber=" + phoneNumber + ", dateOfBirth="
				+ dateOfBirth + ", courseName=" + courseName + "]";
	}
	
}
