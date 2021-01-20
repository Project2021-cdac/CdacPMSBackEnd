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

import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@Table(name = "user_table"/*
							 * TODO Uncomment to add unique constraint to email , uniqueConstraints = {
							 * 
							 * @UniqueConstraint(columnNames = "email") }
							 */)
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(value = Include.NON_ABSENT)
	private Integer id;

	@Column(length = 50)
	@Pattern(regexp = "^[a-zA-Z]{1,50}$", message = "First name must be alphabetic and should be between 1 and 50 characters long!!!")
	private String firstName;

	@Column(length = 50)
	@Pattern(regexp = "^[a-zA-Z]{1,50}$", message = "Last name must be alphabetic and should be between 1 and 50 characters long!!!")
	private String lastName;

	//TODO Add unique constraint once everything is final
	@Column(length = 100)
	@Email
	private String email;

	@Column(length = 200)
//	@Size(min = 4, max = 16, message = "Password must be 4-16 characters long")
	@JsonIgnore
	private String password;

	@Column(length = 15, unique = true)
	@Pattern(regexp = "^[0-9]{10,15}$", message = "Mobile number be between 10 to 15 digits!!!")
	private String phoneNumber;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	@Enumerated(EnumType.STRING)
	@JsonInclude(value = Include.NON_ABSENT)
	private Role role;

	@Enumerated(EnumType.STRING)
	private Course courseName;

	public UserAccount(Integer id) {
		this.id = id;
	}

	public UserAccount(String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth,
			Course courseName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.courseName = courseName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.toUpperCase();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.toUpperCase();
	}

	// TODO setPassword with BCrypt encoded one.
	public void setPassword(String password) {
		this.password = passwordEncoder().encode(password);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", role=" + role + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", phoneNumber=" + phoneNumber + ", dateOfBirth="
				+ dateOfBirth + ", courseName=" + courseName + "]";
	}

}
