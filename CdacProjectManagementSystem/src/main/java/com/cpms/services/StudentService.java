package com.cpms.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.math3.exception.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpms.dto.ProjectDTO;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.Technology;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.ProjectRepository;
import com.cpms.repository.StudentRepository;
import com.cpms.repository.TechnologyRepository;

@Service
@Transactional
public class StudentService implements IStudentService {
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	IProjectService projectService;
	@Autowired
	ITechnologyService technologyService;

	@Override
	public Student getStudentByUserAccount(UserAccount userAccount) {
		System.out.println(userAccount);
		return studentRepository.findByUserAccount(userAccount);
	}

	@Override
	public List<Student> getStudentsWithoutProject() {
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

}