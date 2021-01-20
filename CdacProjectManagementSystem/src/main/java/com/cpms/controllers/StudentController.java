package com.cpms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.ProjectDTO;
import com.cpms.dto.ProjectStatusDTO;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.Task;
import com.cpms.pojos.UserAccount;
import com.cpms.services.IStudentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	IStudentService studentService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
		Student student = studentService.getStudentByUserAccount(new UserAccount(id));
		if (student == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(student, HttpStatus.OK);
		}
	}

	@PostMapping("/createproject")
	public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
		System.out.println(projectDTO);
		Project project = studentService.registerProject(projectDTO);
		if (project == null)
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

		studentService.saveProjectCreationActivity(project);
		return new ResponseEntity<>(project, HttpStatus.CREATED);
	}

	@GetMapping("/noproject")
	public ResponseEntity<?> getStudentsWithoutProject() {
		List<Student> students = studentService.getStudentsWithoutProject();
		if (students.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(students, HttpStatus.OK);
		}
	}
	
	@PostMapping("/createtask/{projectid}")
	public ResponseEntity<?> createTask(@RequestBody Task newtask){
		Task createdTask = studentService.createTask(newtask);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/milestones/:projectid")
	public ResponseEntity<?> getProjectMilstonesAndTaskdetails(@PathVariable Integer projectId){
		List<ProjectStatusDTO> projectStatus = studentService.getProjectMilstonesAndTaskdetails(projectId);
		if(!projectStatus.isEmpty())
			return new ResponseEntity<>(projectStatus, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}
}
