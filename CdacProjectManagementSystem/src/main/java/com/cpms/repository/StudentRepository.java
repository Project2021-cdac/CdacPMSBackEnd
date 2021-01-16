package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpms.pojos.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	public List<Student> findAllByOrderByPrn();
}
