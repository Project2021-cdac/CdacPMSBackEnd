package com.cpms.services;

import java.util.List;

import com.cpms.dto.GuideProjectDTO;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;

public interface IGuideService {
	
	Guide registerGuide(Guide guide);
	Guide assignProject(Integer guideId, Integer projectId);
	List<Project> getProjectsAssignedToGuide(Integer guideId);
	GuideProjectDTO getProjectDetails(Integer projectId);
	List<Guide> getGuideList();
}
