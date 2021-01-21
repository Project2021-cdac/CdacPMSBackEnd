package com.cpms.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Technology;
import com.cpms.repository.TechnologyRepository;

@Service
@Transactional
public class TechnologyService implements ITechnologyService {

	@Autowired 
	TechnologyRepository techRepository;
	
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

	@Override
	public Technology saveTechnology(Technology technology) {
		return techRepository.save(technology);
	}

}
