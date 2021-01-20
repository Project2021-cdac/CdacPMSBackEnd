package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Milestone;
import com.cpms.pojos.Project;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Integer>{
	List<Milestone> findByProjectId(Project project);
}

