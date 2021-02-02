package com.cpms.services;

import java.util.List;

import com.cpms.pojos.Course;
import com.cpms.pojos.Project;

public interface IProjectService {
	
	Project registerProject(Project project);
	List<Project> getProjectsWithNoGuide(Course courseName);
	List<Project> getAllProjectList(Course course);
}
