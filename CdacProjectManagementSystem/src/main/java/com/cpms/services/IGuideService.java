package com.cpms.services;

import java.util.List;

import com.cpms.dto.GuideProjectDTO;
import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.pojos.Session;
import com.cpms.pojos.UserAccount;

public interface IGuideService {
	
	Guide registerGuide(Guide guide);
	Guide assignProject(Integer guideId, Integer projectId);
	List<Project> getProjectsAssignedToGuide(Integer guideId);
	GuideProjectDTO getProjectDetails(Integer projectId);
	List<Guide> getGuideList(Course course);
	Session saveSessionStart(Integer projectId);
	Session saveSessionEnd(Integer sessionId);
	List<Session> getSessionListByProject(Integer projectId);
	Guide getGuideByUserId(UserAccount userAccount);
}
