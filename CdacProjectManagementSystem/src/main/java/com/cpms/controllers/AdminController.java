package com.cpms.controllers;

import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.cpms.dto.ProjectStudentResponseDTO;
import com.cpms.dto.RegisterGuideDTO;
import com.cpms.dto.ResponseMessage;
import com.cpms.fileutils.excelfilehelper.ExcelFileParser;
import com.cpms.pojos.Admin;
import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.pojos.Role;
import com.cpms.pojos.Student;
import com.cpms.pojos.Technology;
import com.cpms.pojos.UserAccount;
import com.cpms.services.IAdminService;
import com.cpms.services.IEmailService;
import com.cpms.services.IExcelFileHelperService;
import com.cpms.services.IGuideService;
import com.cpms.services.IProjectService;
import com.cpms.services.IStudentService;
import com.cpms.services.ITechnologyService;
import com.cpms.services.IUserAccountService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/admin")	
public class AdminController {
	
	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private IAdminService adminService;

	@Autowired
	private IUserAccountService userAcctService;

	@Autowired
	private IExcelFileHelperService excelFileHelperService;

	@Autowired
	private IEmailService emailService;

	@Autowired
	private IGuideService guideService;
	
	@Autowired
	private IStudentService studentService;
	
	@Autowired 
	private ITechnologyService technologyService;
	
	@Autowired
	private IProjectService projectService;

	
	@ApiOperation(value = "Returns List of Students according to course")
	@GetMapping(value = "/students/{coursename}")
	public ResponseEntity<?> getStudentList(@PathVariable(name="coursename") String coursename) {
		List<Student> studentListOrderedByPrn = adminService.getStudentListOrderedByPrn(Course.valueOf(coursename.toUpperCase()));
		if (studentListOrderedByPrn.isEmpty()) {
			logger.info("No student List found.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(studentListOrderedByPrn, HttpStatus.OK);
	}

	//TODO how to avoid multiple registration. FrontEnd Perhaps?
	@PostMapping(value = "/students/register")
	public ResponseEntity<?> registerStudent(@RequestBody MultipartFile file) throws Exception {
		ResponseMessage response = new ResponseMessage();
		if (!file.isEmpty() &&  ExcelFileParser.hasExcelFormat(file)) {
				List<UserAccount> studentUserAccounts = excelFileHelperService.saveToDatabase(file);
				emailService.sendEmail(studentUserAccounts);
				response.setResponseMessage("All Students registered successfully");
		}else {
			logger.info("Either file was not an excel sheet or it was empty. File Content Type: "+file.getContentType());
			response.setResponseMessage("Upload an ExcelFile!!"+file.getContentType());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping(value = "/guides/{coursename}")
	public ResponseEntity<?> getGuideList(@PathVariable(name="coursename") String coursename){
		List<Guide> guideList = guideService.getGuideList(Course.valueOf(coursename.toUpperCase()));
		if (guideList.isEmpty()) {
			logger.info("No guides present in the system while accessing guide list.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(guideList, HttpStatus.OK);
	}
	
	@PostMapping(value = "/guides/register") 
	ResponseEntity<?> registerGuides(@RequestBody RegisterGuideDTO guideUser){
		if(null != guideUser && !guideUser.getTechnologylist().isEmpty()) {
			guideUser.getGuidedata().setRole(Role.ROLE_GUIDE);
			UserAccount registeredGuideAcct = userAcctService.registerUser(guideUser.getGuidedata());
			List<Integer> technologies = guideUser.getTechnologylist();
			List<Technology> technologyDbList = technologyService.findTechnologiesById(technologies); 
			Guide guide = new Guide();
			guide.setInSession(false);
			guide.setUserAccount(registeredGuideAcct);
			guide.getTechnologies().addAll(technologyDbList);
			guide = guideService.registerGuide(guide);
			return new ResponseEntity<>(guide, HttpStatus.CREATED);
		}
		logger.error("Guide registration error: guide registration failed.");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = "/projects/list/{coursename}")
	public ResponseEntity<?> getProjectList(@PathVariable String coursename){
		List<Project> projectList = projectService.getAllProjectList(Course.valueOf(coursename.toUpperCase()));
		if(!projectList.isEmpty()) {
			List<ProjectStudentResponseDTO> projectResponse = new ArrayList<>();
			for(Project project: projectList) {
				List<Student> teamMembers = studentService.getStudentListByProject(new Project(project.getId()));
				ProjectStudentResponseDTO response = new ProjectStudentResponseDTO(project, teamMembers);			
				projectResponse.add(response);
			}
			return new ResponseEntity<>(projectResponse, HttpStatus.OK);
		}
		logger.info("No projects registered till now.");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "/teamsize/{coursename}")
	public ResponseEntity<?> getTeamsize(@PathVariable(name="coursename") String coursename) {
		Optional<Admin> adminAcct = adminService.getAdminByCourse(Course.valueOf(coursename));
		if(adminAcct.isPresent()) {
			return new ResponseEntity<> (adminAcct.get().getProjectMinSize(), HttpStatus.OK);
		}
		logger.info("Admin account for course: '"+coursename+"' doesn't exists.");
		return new ResponseEntity<> (HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "/{coursename}/setsize/{size}") 
	public ResponseEntity<?> setTeamSize(@PathVariable(name="coursename") String coursename, @PathVariable(name = "size") int projectMinSize){
		Optional<Admin> adminAcct = adminService.getAdminByCourse(Course.valueOf(coursename));
		if(adminAcct.isPresent()){
			Admin admin = adminAcct.get();
			if(admin.getProjectMinSize() != projectMinSize) {
				admin.setProjectMinSize(projectMinSize);
				return new ResponseEntity<> (adminService.save(admin).getProjectMinSize(), HttpStatus.OK);
			}
			return new ResponseEntity<> (adminAcct, HttpStatus.CONFLICT);
		}
		logger.error("No admin for given course: "+coursename+" found.");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
