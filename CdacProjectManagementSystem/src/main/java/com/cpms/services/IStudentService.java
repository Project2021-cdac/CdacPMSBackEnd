package com.cpms.services;

import java.util.List;

import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;

public interface IStudentService {
	public Student getStudentByUserAccount(UserAccount userAccount);
	public List<Student> getStudentsWithoutProject();
}
