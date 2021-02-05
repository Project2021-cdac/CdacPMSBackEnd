package com.cpms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	List<Project> findByGuide(Guide guide);
	@Query("SELECT project FROM Project project WHERE project.teamLead.userAccount.courseName = :courseName and project.guide=null")
	List<Project> findAllWithNoGuide(@Param("courseName") Course courseName);
	Optional<Project> findById(Integer projectid);
}
