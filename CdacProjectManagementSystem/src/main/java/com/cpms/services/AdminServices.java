package com.cpms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Guide;
import com.cpms.pojos.Student;
import com.cpms.repository.StudentRepository;
import com.cpms.repository.UserAccountRepository;

@Service
public class AdminServices implements IAdminServices {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private UserAccountRepository userAcctRepository;
	
	@Override
	public List<Student> getStudentListOrderedByPrn() {
		return studentRepository.findAllByOrderByPrn();
		
	}

	@Override
	public String registerStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Guide> getGuideList() {
		return userAcctRepository.getUserAccountofGuides();
	}

}
