package com.cpms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Technology;
import com.cpms.repository.TechnologyRepository;

@Service
public class TechnologyService implements ITechnologyService {

	@Autowired 
	TechnologyRepository techRepository;
	
	@Override
	public List<Technology> getAllTechnology() {
		return techRepository.findAllByOrderByTechnologyName();
	}

}
