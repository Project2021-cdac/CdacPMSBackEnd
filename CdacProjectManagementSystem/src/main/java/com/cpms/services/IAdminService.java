package com.cpms.services;

import java.util.List;

import com.cpms.pojos.Admin;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;

public interface IAdminService {

	List<Student> getStudentListOrderedByPrn();
	String registerStudent();
	List<Guide> getGuideList();
	Integer getProjectMinSize();
	Admin findByUserAccount(UserAccount account);
	public Admin save(Admin admin);
}
