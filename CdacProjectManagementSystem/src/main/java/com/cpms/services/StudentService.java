package com.cpms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.dto.ProjectDTO;
import com.cpms.dto.ProjectStatusDTO;
import com.cpms.pojos.Activity;
import com.cpms.pojos.Course;
import com.cpms.pojos.Milestone;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.Task;
import com.cpms.pojos.Technology;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.MilestoneRepository;
import com.cpms.repository.StudentRepository;
import com.cpms.repository.TaskRepository;

@Service
@Transactional
public class StudentService implements IStudentService {
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	IProjectService projectService;
	@Autowired
	ITechnologyService technologyService;
	@Autowired
	IActivityService activityService;
	TaskRepository taskRepository;
	@Autowired
	MilestoneRepository milestoneRepository;

	@Override
	public Student getStudentByUserAccount(UserAccount userAccount) {
		System.out.println(userAccount);
		return studentRepository.findByUserAccount(userAccount);
	}

	@Override
	public List<Student> getStudentsWithoutProject(/*Course course*/) {
		return studentRepository.findByProjectIsNull();
	}

	@Override
	public Project registerProject(ProjectDTO projectDTO) {
		Optional<Student> optional = studentRepository.findById(projectDTO.getTeamLead());
		Student teamLead = null;
		if (optional.isPresent()) {
			teamLead = optional.get();
		} else {
			return null;
		}

		Project project = projectDTO.getProject();
		projectService.registerProject(project);
		//why not addAll?
		List<Technology> technologies = technologyService.findTechnologiesById(projectDTO.getTechnologies());
		for (Technology technology : technologies) {
			project.addTechnology(technology);
		}

		teamLead.setProject(project);
		List<Student> students = studentRepository.findAllById(projectDTO.getStudentPrns());
		for (Student student : students) {
			student.setProject(project);
		}

		return project;
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Activity saveProjectCreationActivity(Project project) {

		String activityDescription = "Project " + project.getId() + ". " + project.getTitle()
				+ " created with group members ";

		List<Student> students = studentRepository.findByProject(project);

		for (Student eachStudent : students) {
			activityDescription += eachStudent.getPrn() + " ";
		}
		
		return activityService.createActivity(activityDescription, project.getId());
	}
	
	@Override
	public Task createTask(Task newtask) {
		return taskRepository.save(newtask);
	}
	
	@Override
	public List<ProjectStatusDTO> getProjectMilstonesAndTaskdetails(int projectId){
		List<ProjectStatusDTO> projectStatus = new ArrayList<>();
		List<Milestone> milestones =  milestoneRepository.findAll();
		for(Milestone m : milestones) {
			List<Task> tasklist = taskRepository.findByMilestone(m);
//			if(!tasklist.isEmpty())
				projectStatus.add(new ProjectStatusDTO(m, tasklist));
//			else
//				projectStatus.add(new ProjectStatusDTO(m, tasklist));
		}
		return projectStatus;
	}

}
