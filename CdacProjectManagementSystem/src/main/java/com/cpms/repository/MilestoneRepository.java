package com.cpms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Milestone;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Integer>{
}

