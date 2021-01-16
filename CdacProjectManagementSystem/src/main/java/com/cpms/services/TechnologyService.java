package com.cpms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cpms.pojos.Technology;
import com.cpms.repository.TechnologyRepository;

public class TechnologyService implements ITechnologyService {

	@Autowired 
	TechnologyRepository techRepository;
	
	@Override
	public List<Technology> getAllTechnology() {
		return techRepository.findAllByOrderByTechnologyName();
	}

}
