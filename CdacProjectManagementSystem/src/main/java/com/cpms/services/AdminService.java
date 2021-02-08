package com.cpms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Admin;
import com.cpms.pojos.Course;
import com.cpms.pojos.Student;
import com.cpms.repository.AdminRepository;
import com.cpms.repository.StudentRepository;

@Service
public class AdminService implements IAdminService {

	@Autowired
	private StudentRepository studentRepository;
	

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public List<Student> getStudentListOrderedByPrn(Course coursename) {
		List<Student> findAllByOrderByPrn = studentRepository.findAllByOrderByPrn(coursename);		
		return findAllByOrderByPrn;
	}
	
	@Override
	public Admin save(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public Optional<Admin> getAdminByCourse(Course course) {
		return adminRepository.findByCourse(course);
	}
	
	
}
