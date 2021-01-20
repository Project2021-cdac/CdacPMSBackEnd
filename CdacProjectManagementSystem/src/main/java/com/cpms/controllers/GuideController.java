package com.cpms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.GuideProjectDTO;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.services.IGuideService;
import com.cpms.services.IProjectService;

@RestController
@RequestMapping("/guide")
public class GuideController {

	@Autowired
	IGuideService service;
	@Autowired
	IProjectService projectService;

	@GetMapping("/availableprojects")
	public ResponseEntity<?> fetchAvailableProjects() {
		List<Project> projects = projectService.getProjectsWithNoGuide();
		if (!projects.isEmpty()) {
			return new ResponseEntity<>(projects, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/select")
	public ResponseEntity<?> chooseProject(@RequestParam int guideId, @RequestParam int projectId) {
		Guide guide = service.assignProject(guideId, projectId);
		if (guide != null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@GetMapping("/projects")
	public ResponseEntity<?> getProjectList(@RequestParam Integer guideId) {
		List<Project> projects = service.getProjectsAssignedToGuide(guideId);
		if (!projects.isEmpty()) {
			return new ResponseEntity<>(projects, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/project")
	public ResponseEntity<?> fetchProjectDetails(@RequestParam Integer projectId) {
		GuideProjectDTO guideProjectDTO = service.getProjectDetails(projectId);
		if(guideProjectDTO != null) {
			return new ResponseEntity<>(guideProjectDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
