package com.cpms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Admin;
import com.cpms.pojos.Course;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select adm from Admin adm join fetch adm.userAccount u where u.courseName = :course")
	Optional<Admin> findByCourse(@Param("course") Course course);
}
