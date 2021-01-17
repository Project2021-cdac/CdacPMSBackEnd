package com.cpms.services;

import java.util.List;

import com.cpms.pojos.Guide;
import com.cpms.pojos.Student;

public interface IAdminServices {

	List<Student> getStudentListOrderedByPrn();
	String registerStudent();
	List<Guide> getGuideList();
}
