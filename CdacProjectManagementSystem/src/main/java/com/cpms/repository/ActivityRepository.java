package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Activity;
import com.cpms.pojos.Project;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
	List<Activity> findByProjectId(Project project);
}
