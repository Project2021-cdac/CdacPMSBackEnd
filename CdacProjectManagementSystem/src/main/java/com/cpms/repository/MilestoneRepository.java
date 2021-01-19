package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Milestone;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Integer> {
	
	@Query("select m from Milestone m join fetch m.projectId where m.projectId = :projectId")
	List<Milestone> fetchByProject(Integer project);
}
//select * from task join milestone on milestone.id=task.milestone.id join project on milestone.project.id = project.id