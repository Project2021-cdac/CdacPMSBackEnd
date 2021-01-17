package com.cpms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Project;
import com.cpms.repository.ProjectRepository;

@Service
public class ProjectService implements IProjectService {

	@Autowired 
	ProjectRepository repository;
	
	@Override
	public List<Project> getAllProjectList() {
		return repository.findAllByOrderById();
	}

}
