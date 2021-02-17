package com.cpms.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.pojos.Activity;
import com.cpms.pojos.Project;
import com.cpms.repository.ActivityRepository;
import com.cpms.repository.ProjectRepository;

@Service
@Transactional
public class ActivityService implements IActivityService {

	@Autowired
	ActivityRepository activityRespository;
	@Autowired
	ProjectRepository projectRepository;
	
	
	/**
	 *@param projectId the project for which activity is to be created
	 *@return Activity
	 */
	@Override
	public Activity createActivity(String activityDescription, Integer projectId) {
		Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());
		String description = activityDescription;
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(optionalProject.isPresent()) {
			Activity activity = new Activity(createdOn, description, optionalProject.get());
			return activityRespository.save(activity);
		}
		return null;
	}

}
