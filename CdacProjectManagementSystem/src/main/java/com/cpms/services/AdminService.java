package com.cpms.services;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Admin;
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
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public List<Student> getStudentListOrderedByPrn() {
		 List<Student> findAllByOrderByPrn = studentRepository.findAllByOrderByPrn();		
//		 for (Student student : findAllByOrderByPrn) {
//			System.out.println(student);
//		}
		return findAllByOrderByPrn;
	}

	@Override
	public List<UserAccount> getGuideList() {
		return  userAcctRepository.getUserAccountofGuides();
	}

	
	@Override
	public Admin save(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public Optional<Admin> getAdminByUserAccount(UserAccount userAccount) {
		return adminRepository.findByUserAccount(userAccount);
	}
	
	
}
