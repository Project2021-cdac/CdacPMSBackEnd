package com.cpms.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.dto.ProjectDTO;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.Technology;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.ProjectRepository;
import com.cpms.repository.StudentRepository;

@Service
@Transactional
public class StudentService implements IStudentService {
	@Autowired
	StudentRepository repo;
	@Autowired
	ProjectRepository projectRepo;
	@Autowired
	ITechnologyService technologyService;
	
	@Override
	public Student getStudentByUserAccount(UserAccount userAccount) {
		return repo.findByUserAccount(userAccount);
	}

	@Override
	public List<Student> getStudentsWithoutProject() {
		return repo.findByProjectIsNull();
	}
	
	@Override
	public Project registerProject(ProjectDTO projectDTO) {
		Optional<Student>optional = repo.findById(projectDTO.getTeamLead());
		Student teamLead = null;
		if(optional.isPresent()) {
			teamLead = optional.get();
		} else {
			return null;
		}
		 
		Project project = projectDTO.getProject();
		projectRepo.save(project);
		
		List<Technology> technologies = technologyService.findTechnologiesById(projectDTO.getTechnologies());
		for (Technology technology : technologies) {
			project.addTechnology(technology);
		}
		
		teamLead.setProject(project);
		List<Student> students = repo.findAllById(projectDTO.getStudentPrns());
		for (Student student : students) {
			student.setProject(project);
		}
		
		return project;
	}
}
