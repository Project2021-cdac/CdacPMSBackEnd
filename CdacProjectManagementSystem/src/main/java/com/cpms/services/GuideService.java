package com.cpms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.dto.GuideProjectDTO;
import com.cpms.pojos.Activity;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Milestone;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.repository.ActivityRepository;
import com.cpms.repository.GuideRepository;
import com.cpms.repository.MilestoneRepository;
import com.cpms.repository.ProjectRepository;
import com.cpms.repository.StudentRepository;

@Service
@Transactional
public class GuideService implements IGuideService {

	@Autowired
	private GuideRepository guideRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private IActivityService activityService;

	@Override
	public Guide registerGuide(Guide guide) {
		return guideRepository.save(guide);
	}

	@Override
	public Guide assignProject(Integer guideId, Integer projectId) {

		Optional<Guide> optionalGuide = guideRepository.findById(guideId);
		Optional<Project> optionalProject = projectRepository.findById(projectId);

		if (optionalGuide.isPresent() && optionalProject.isPresent()) {
			Guide guide = optionalGuide.get();
			Project project = optionalProject.get();
			project.setGuide(guide);

			String activityDescription = "Guide " + guide.getUserAccount().getFirstName() + " "
					+ guide.getUserAccount().getLastName() + " assigned to project " + project.getId() + ". "
					+ project.getTitle();

			activityService.createActivity(activityDescription, projectId);
			return guide;
		}

		return null;
	}

	@Override
	public List<Project> getProjectsAssignedToGuide(Integer guideId) {
		return projectRepository.findByGuide(new Guide(guideId));
	}

	@Override
	public GuideProjectDTO getProjectDetails(Integer projectId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if (optionalProject.isPresent()) {
			Project project = optionalProject.get();
			List<Activity> activities = activityRepository.findByProjectId(project);
			List<Milestone> milestones = milestoneRepository.findByProjectId(project);
			//List<Student> students = studentRepository.findByProject(project);
			List<Student> students = studentRepository.findByProject(project);
			List<String> studentNames = new ArrayList<>();
			
			for(Student student : students) {
				String fullName = student.getUserAccount().getFirstName() + " " + student.getUserAccount().getLastName();
				studentNames.add(fullName);
			}
			return new GuideProjectDTO(project, activities, milestones, studentNames);
		}
		return null;
	}
	
	public List<Guide> getGuideList() {
		return guideRepository.findAll();
	}
}
