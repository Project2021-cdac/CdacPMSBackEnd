package com.cpms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.pojos.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {
	List<Session> findByProject(Project project);
	Session findByGuideAndEndTimeIsNull(Guide guide);
}
