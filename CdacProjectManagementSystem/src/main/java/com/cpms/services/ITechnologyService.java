package com.cpms.services;

import java.util.List;

import com.cpms.pojos.Technology;

public interface ITechnologyService {
	List<Technology> listTechnologies();
	List<Technology> findTechnologiesById(List<Integer> technologyIds);
	List<Technology> getAllTechnology();
	Technology saveTechnology(Technology technology);
	
}
