package com.cpms.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Admin;
import com.cpms.pojos.Course;
import com.cpms.pojos.Project;
import com.cpms.repository.AdminRepository;
import com.cpms.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService implements IProjectService {

	@Autowired 
	ProjectRepository repository;
	@Autowired
	AdminRepository adminRepository;
	
	@Override
	public Project registerProject(Project project) {
		return repository.save(project);
	}

	@Override
	public List<Project> getAllProjectList(Course course) {
		return repository.findAllByOrderById(course);
	}

	@Override
	public List<Project> getProjectsWithNoGuide(Course courseName) {
		return repository.findAllWithNoGuide(courseName);
	}
	
	@Override
	public boolean projectTeamSizeisValid(Course courseName, int teamSize){
		Optional<Admin> optionalAdmin =  adminRepository.findByCourse(courseName);
		
		if(optionalAdmin.isPresent()) {
			Admin admin = optionalAdmin.get();
			if(teamSize >= admin.getProjectMinSize()) {
				return true;
			}
		}
		
		return false;
	}

	@Override 
	public Optional<Project> getProjectById(Integer projectid){
		return repository.findById(projectid);
	}
	
}
