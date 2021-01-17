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
	
	@Override
	public List<Technology> listTechnologies() {
		System.out.println(techRepository.findAll());
		return techRepository.findAll();
	}
	
	@Override
	public List<Technology> findTechnologiesById(List<Integer> technologyIds) {
		List<Technology> technologies = techRepository.findAllById(technologyIds);
		return technologies;
	}

}
