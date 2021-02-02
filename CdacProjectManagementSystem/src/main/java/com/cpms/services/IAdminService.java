package com.cpms.services;

import java.util.List;
import java.util.Optional;

import com.cpms.pojos.Admin;
import com.cpms.pojos.Course;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;

public interface IAdminService {

	List<Student> getStudentListOrderedByPrn(Course coursename);
//	List<UserAccount> getGuideList();
	Optional<Admin> getAdminByUserAccount(UserAccount account);
	Admin save(Admin admin);
}
