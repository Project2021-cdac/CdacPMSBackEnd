package com.cpms.services;

import java.util.List;

import com.cpms.pojos.Project;

public interface IProjectService {
	
	Project registerProject(Project project);
	List<Project> getAllProjectList();
	
}
