package com.cpms.services;

import java.util.List;
import java.util.Optional;

import com.cpms.pojos.Admin;
import com.cpms.pojos.Course;
import com.cpms.pojos.Student;

public interface IAdminService {

	List<Student> getStudentListOrderedByPrn(Course coursename);
	Optional<Admin> getAdminByCourse(Course course);
	Admin save(Admin admin);
}
