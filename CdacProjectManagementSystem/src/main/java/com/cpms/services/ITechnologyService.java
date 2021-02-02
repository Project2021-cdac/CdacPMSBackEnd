package com.cpms.services;

import java.util.List;

import com.cpms.pojos.Technology;

public interface ITechnologyService {
	List<Technology> listTechnologies();
	List<Technology> findTechnologiesById(List<Integer> technologyIds);
	Technology saveTechnology(Technology technology);
	
}
