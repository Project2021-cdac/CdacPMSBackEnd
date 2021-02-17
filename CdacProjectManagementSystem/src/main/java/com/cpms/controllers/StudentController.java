package com.cpms.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.cpms.dto.ProjectStudentResponseDTO;
import com.cpms.pojos.Activity;
import com.cpms.pojos.Course;
import com.cpms.pojos.Project;
import com.cpms.pojos.Status;
import com.cpms.pojos.Student;
import com.cpms.pojos.Task;
import com.cpms.pojos.UserAccount;
import com.cpms.services.IActivityService;
import com.cpms.services.IProjectService;
import com.cpms.services.IStudentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

	private Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	IStudentService studentService;

	@Autowired
	IProjectService projectService;
	
	@Autowired
	IActivityService activityService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
		Optional<Student> student = studentService.getStudentByUserAccount(new UserAccount(id));
		if (student.isPresent()) {
			return new ResponseEntity<>(student.get(), HttpStatus.OK);
		} else {
			logger.info("Student with given user account id: "+id+" not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @param projectDTO a wrapper around project details, technologies and students
	 *                   associated with project.
	 * @see ProjectDTO
	 * @return ResponseEntity object with Project instance and HttpStatus.CREATED
	 *         status in case of success.
	 */
	@PostMapping("/createproject/{courseName}")
	public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO, @PathVariable("courseName") String courseName) {
		if(projectService.projectTeamSizeisValid(Course.valueOf(courseName), projectDTO.getStudentPrns().size() + 1 )) {
			ProjectStudentResponseDTO projectResponseDTO = studentService.registerProject(projectDTO);
			System.out.println("teamLead userAccount" + projectResponseDTO.getProject().getTeamLead().getProject());
			studentService.saveProjectCreationActivity(projectResponseDTO.getProject());
			return new ResponseEntity<>(projectResponseDTO, HttpStatus.CREATED);
		} 
		logger.error("Project registration failed due to invalid team size");
		return new ResponseEntity<>("Invalid team size", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/noproject/{courseName}")
	public ResponseEntity<?> getStudentsWithoutProject(@PathVariable("courseName") String courseName) {
		try {
			List<Student> students = studentService.getStudentsWithoutProject(Course.valueOf(courseName.toUpperCase()));
			if (students.isEmpty()) {
				logger.info("All students have formed project group hence, student list empty");
				return new ResponseEntity<>(students, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(students, HttpStatus.OK);
			}
		} catch (IllegalArgumentException | NullPointerException exception) {
			exception.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/createtask/{projectid}/{studentid}")
	public ResponseEntity<?> createTask(@RequestBody Task newtask, @PathVariable(name="studentid") Long studentId, @PathVariable(name="projectid") Integer projectId) {
		Optional<Project> project = projectService.getProjectById(projectId);
		Optional<Student> student = studentService.getStudentByPRN(studentId);
		if(student.isPresent() && project.isPresent()) {
			Student member = student.get();
			newtask.setCreatedBy(member);
			newtask.setCreatedOn(LocalDate.now());
			newtask.setProject(project.get());
			activityService.createActivity("Task created by "+member.getUserAccount().getFirstName()+
					" "+member.getUserAccount().getLastName(), projectId);
			return new ResponseEntity<>(studentService.createTask(newtask), HttpStatus.CREATED);
		}
		logger.error("Task creation failed, either student PRN: "+studentId+" or project id "+projectId+" is invalid.");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/endtask/{taskid}")
	public ResponseEntity<?> endTask(@PathVariable(name="taskid") Integer taskId){
		Optional<Task> task = studentService.getTask(taskId);
		if(task.isPresent()) {
			Task t = task.get();
			if(!t.getStatus().equals(Status.COMPLETED)) {
				t.setStatus(Status.COMPLETED);
				Activity activity = activityService.createActivity("Task: "+t.getDescription()+" ended", t.getProject().getId());
				if(activity!=null)
					return new ResponseEntity<>(activity, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		logger.info("Task could not be ended, invalid task id: "+taskId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/task/{studentprn}")
	public ResponseEntity<?> getTask(@PathVariable(name="studentprn") Long studentprn){
		List<Task> studentTaskList = studentService.getTasksofStudent(new Student(studentprn));
		if(studentTaskList.isEmpty()) {
			logger.info("Task list is empty for the given student prn: "+studentprn);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(studentTaskList, HttpStatus.OK);
	}
}
