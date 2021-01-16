package com.cpms.wrapperclasses;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;


@Getter
@Setter
public class UserAccountStudentWrapper {
	
	private List<UserAccount> userAccountList;
	private List<Student> studentList;
	
	public UserAccountStudentWrapper() {
		userAccountList=null;
		studentList=null;
	}
}
