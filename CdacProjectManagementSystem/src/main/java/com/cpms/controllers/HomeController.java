package com.cpms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.GuideProjectDTO;
import com.cpms.pojos.Course;
import com.cpms.pojos.Project;
import com.cpms.pojos.Task;
import com.cpms.pojos.Technology;
import com.cpms.repository.MilestoneRepository;
import com.cpms.repository.TaskRepository;
import com.cpms.services.IGuideService;
import com.cpms.services.ITechnologyService;

/**
 * @author karun
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	private ITechnologyService service;
	@Autowired
	private IGuideService guideService;
	
	@Autowired
	private MilestoneRepository milestoneRepository;

	@Autowired
	private TaskRepository taskRepository;


	@GetMapping("course/list")
	public ResponseEntity<?>  listCourses() {
		List<String> courses = new ArrayList<>();
		for (Course course : Course.values()) {
			courses.add(course.toString());
		}
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

	@GetMapping("technology/list")
	public ResponseEntity<?> listTechnologies() {
		List <Technology> technologies = service.listTechnologies();
		if(technologies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(technologies, HttpStatus.OK);
		}
	}
	
	
	@GetMapping("milestone/list")
	public ResponseEntity<?> getAllMilestones() {
		return new ResponseEntity<>(milestoneRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("project")
	public ResponseEntity<?> fetchProjectDetails(@RequestParam Integer projectId) {
		GuideProjectDTO guideProjectDTO = guideService.getProjectDetails(projectId);
		if (guideProjectDTO != null) {
			return new ResponseEntity<>(guideProjectDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("milestones/{projectid}")
	public ResponseEntity<?> getMilestonesOfProject(@PathVariable(name="projectid")Integer projectid){
		List<Task> tasklist = taskRepository.findByProject(new Project(projectid)); 
		return new ResponseEntity<>(tasklist, HttpStatus.OK);
	}
}
