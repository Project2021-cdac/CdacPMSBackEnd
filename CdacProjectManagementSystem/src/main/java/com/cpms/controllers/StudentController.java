package com.cpms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.ProjectDTO;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;
import com.cpms.services.IStudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	IStudentService service;

	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
		Student student = service.getStudentByUserAccount(new UserAccount(id));
		if (student == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(student, HttpStatus.OK);
		}
	}
	
	@PostMapping("/createproject")
	public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
		 System.out.println(projectDTO);
		 Project project = service.registerProject(projectDTO);
		 if(project == null)
			 return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		 return new ResponseEntity<>(project, HttpStatus.CREATED);
	}	
	
	@GetMapping("/noproject")
	public ResponseEntity<?> getStudentsWithoutProject() {
		List<Student> students = service.getStudentsWithoutProject();
		if (students.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(students, HttpStatus.OK);
		}
	}
}
