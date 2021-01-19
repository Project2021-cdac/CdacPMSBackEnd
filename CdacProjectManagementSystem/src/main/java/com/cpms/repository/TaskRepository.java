package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Milestone;
import com.cpms.pojos.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
	List<Task> findByMilestone(Milestone milestone);
}
