package com.cpms.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.dto.CurrentSessionDTO;
import com.cpms.dto.ObjectMessageDTO;
import com.cpms.dto.ResponseMessage;
import com.cpms.dto.SessionMessageDTO;
import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.pojos.Session;
import com.cpms.pojos.UserAccount;
import com.cpms.services.IGuideService;
import com.cpms.services.IProjectService;

@RestController
@RequestMapping("/guide")
@CrossOrigin(origins = "*")
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
		Optional<Guide> guide = service.assignProject(guideId, projectId);
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

	@PostMapping("/startsession")
	public ResponseEntity<?> startSession(@RequestParam Integer projectId) {
		Session session = service.saveSessionStart(projectId);
		System.out.println("Session object =  " + session);
		
		//SessionMessageDTO response = new SessionMessageDTO(session, session.getProject(), "session started");
		if (session == null) {
			return new ResponseEntity<>(new ResponseMessage("failed to start session"), HttpStatus.BAD_REQUEST);
		} else {
			SessionMessageDTO response = new SessionMessageDTO(session, session.getProject(), "session started");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/endsession")
	public ResponseEntity<?> endSession(@RequestParam Integer sessionId) {
		Session session = service.saveSessionEnd(sessionId);
		if (session != null) {
			return new ResponseEntity<>(new ObjectMessageDTO(session, "session ended"),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(new ResponseMessage("failed to end session"), HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getsession/{guideId}")
	public ResponseEntity<?> getLatestSession(@PathVariable Integer guideId) {
		CurrentSessionDTO sessionDTO = service.getActiveSession(guideId);
		if (sessionDTO != null) {
			return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
