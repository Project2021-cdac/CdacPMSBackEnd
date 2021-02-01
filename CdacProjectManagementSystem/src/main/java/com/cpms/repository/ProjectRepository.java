package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	@Query("select p from Project p join fetch p.teamLead t join fetch t.userAccount u "
			+ "where u.courseName = :course order by p.id")
	List<Project> findAllByOrderById(Course course);
	List<Project> findAllByGuideIsNull();
	List<Project> findAllByGuideNotNull();
	List<Project> findByGuide(Guide guide);
}
