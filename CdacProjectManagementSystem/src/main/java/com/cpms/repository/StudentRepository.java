package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	public List<Student> findAllByOrderByPrn();
	Student findByUserAccount(UserAccount userAccount);
	List<Student> findByProjectIsNull();
}
