package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findAllByOrderById();
	//List<Project> findAllByGuideIsNull();
	List<Project> findAllByGuideNotNull();
	List<Project> findByGuide(Guide guide);
	@Query("SELECT project FROM Project project WHERE project.teamLead.userAccount.courseName = :courseName")
	List<Project> findAllWithNoGuide(@Param("courseName") Course courseName);
}
