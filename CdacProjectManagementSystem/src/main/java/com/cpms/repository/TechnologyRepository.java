package com.cpms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Technology;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Integer> {

}
