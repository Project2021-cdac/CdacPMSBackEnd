package com.cpms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cpms.pojos.Technology;
import com.cpms.repository.TechnologyRepository;

@Repository
public class TechnologyServiceImpl implements ITechnologyService {

	@Autowired
	TechnologyRepository repo;

	@Override
	public List<Technology> listTechnologies() {
		System.out.println(repo.findAll());
		return repo.findAll();
	}

}
