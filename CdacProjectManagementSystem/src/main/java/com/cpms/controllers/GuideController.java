package com.cpms.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.ObjectMessageDTO;
import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
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
		Optional <Guide> guide = service.assignProject(guideId, projectId);
		if (guide.isPresent()) {
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
			ObjectMessageDTO response = new ObjectMessageDTO(service.saveSessionStart(projectId), "session started");
			return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/endsession")
	public ResponseEntity<?> endSession(@RequestParam Integer sessionId) {
		ObjectMessageDTO response = new ObjectMessageDTO(service.saveSessionEnd(sessionId), "session ended");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getGuide(@PathVariable Integer userId) {
		Optional<Guide> guide = service.getGuideByUserId(new UserAccount(userId));
		if (guide.isPresent()) {
			return new ResponseEntity<>(guide.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Invalid user id", HttpStatus.NO_CONTENT);
	}
}
