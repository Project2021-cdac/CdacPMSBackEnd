package com.cpms.services;

import java.util.List;
import java.util.Optional;

import com.cpms.dto.CurrentSessionDTO;
import com.cpms.dto.GuideProjectDTO;
import com.cpms.dto.SessionMessageDTO;
import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.pojos.Session;
import com.cpms.pojos.UserAccount;

public interface IGuideService {
	
	Guide registerGuide(Guide guide);
	Optional<Guide> assignProject(Integer guideId, Integer projectId);
	List<Project> getProjectsAssignedToGuide(Integer guideId);
	GuideProjectDTO getProjectDetails(Integer projectId);
	List<Guide> getGuideList(Course course);
	Session saveSessionStart(Integer projectId);
	Session saveSessionEnd(Integer sessionId);
	List<Session> getSessionListByProject(Integer projectId);
	Optional<Guide> getGuideByUserId(UserAccount userAccount);
	CurrentSessionDTO getActiveSession(Integer guideId);
}
