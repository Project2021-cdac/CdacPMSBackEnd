package com.cpms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.GuideProjectDTO;
import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.pojos.Session;
import com.cpms.pojos.UserAccount;
import com.cpms.services.IGuideService;
import com.cpms.services.IProjectService;

@RestController
@RequestMapping("/guide")
public class GuideController {

	@Autowired
	IGuideService service;
	@Autowired
	IProjectService projectService;

	@GetMapping("/availableprojects/{courseName}")
	public ResponseEntity<?> fetchAvailableProjects(@PathVariable("courseName") String courseName) {
		try {
			List<Project> projects = projectService.getProjectsWithNoGuide(Course.valueOf(courseName.toUpperCase()));
			if (!projects.isEmpty()) {
				return new ResponseEntity<>(projects, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IllegalArgumentException | NullPointerException exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/select")
	public ResponseEntity<?> chooseProject(@RequestParam int guideId, @RequestParam int projectId) {
		Guide guide = service.assignProject(guideId, projectId);
		if (guide != null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/projects")
	public ResponseEntity<?> getProjectList(@RequestParam Integer guideId) {
		List<Project> projects = service.getProjectsAssignedToGuide(guideId);
		if (!projects.isEmpty()) {
			return new ResponseEntity<>(projects, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

//	@GetMapping("/project")
//	public ResponseEntity<?> fetchProjectDetails(@RequestParam Integer projectId) {
//		GuideProjectDTO guideProjectDTO = service.getProjectDetails(projectId);
//		if (guideProjectDTO != null) {
//			return new ResponseEntity<>(guideProjectDTO, HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}

	@PostMapping("/startsession")
	public ResponseEntity<?> startSession(@RequestParam Integer projectId) {
		Session session = service.saveSessionStart(projectId);

		if (session != null) {
			return new ResponseEntity<>(session, HttpStatus.OK);
		}
		return new ResponseEntity<>("Cannot create a new session when another is running", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/endsession")
	public ResponseEntity<?> endSession(@RequestParam Integer sessionId) {
		Session session = service.saveSessionEnd(sessionId);
		if (session != null) {
			return new ResponseEntity<>(session, HttpStatus.OK);
		}
		return new ResponseEntity<>("No session is active as of now", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getGuide(@PathVariable Integer userId) {
		Guide guide = service.getGuideByUserId(new UserAccount(userId));
		if (guide != null) {
			return new ResponseEntity<>(guide, HttpStatus.OK);
		}
		return new ResponseEntity<>("Invalid user id", HttpStatus.NO_CONTENT);
	}
}
