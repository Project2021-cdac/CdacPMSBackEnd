package com.cpms.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements IStudentService {
	@Autowired
	StudentRepository repo;
	
	@Override
	public Student getStudentByUserAccount(UserAccount userAccount) {
		return repo.findByUserAccount(userAccount);
	}

	@Override
	public List<Student> getStudentsWithoutProject() {
		return repo.findByProjectIsNull();
	}

}
