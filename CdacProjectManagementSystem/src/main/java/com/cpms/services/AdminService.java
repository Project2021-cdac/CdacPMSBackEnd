package com.cpms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Admin;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.AdminRepository;
import com.cpms.repository.StudentRepository;
import com.cpms.repository.UserAccountRepository;

@Service
public class AdminService implements IAdminService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private UserAccountRepository userAcctRepository;
	
	private AdminRepository adminRepository;
	
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

	@Override
	public Integer getProjectMinSize() {
		return null;
	}

	@Override
	public Admin save(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public Admin findByUserAccount(UserAccount account) {
		return adminRepository.findByUserAccount(account);
	}
	
	
}
