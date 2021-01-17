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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.dto.ResponseMessage;
import com.cpms.fileutils.excelfilehelper.ExcelFileParser;
import com.cpms.pojos.Admin;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.Technology;
import com.cpms.pojos.UserAccount;
import com.cpms.services.IAdminService;
import com.cpms.services.IEmailService;
import com.cpms.services.IExcelFileHelperService;
import com.cpms.services.IGuideService;
import com.cpms.services.IProjectService;
import com.cpms.services.ITechnologyService;
import com.cpms.services.IUserAccountService;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

//	private static List<Technology> technologylist;
	
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
	private ITechnologyService technologyService;
	
	@Autowired
	private IProjectService projectService;
	
	
	@GetMapping("/students")
	public ResponseEntity<?> getStudentList() {
		List<Student> studentListOrderedByPrn = adminService.getStudentListOrderedByPrn();
		if (studentListOrderedByPrn.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(studentListOrderedByPrn, HttpStatus.OK);
	}

	@PostMapping("/students/register")
	public ResponseEntity<?> registerStudent(@RequestParam MultipartFile file) throws Exception {
		String message = null;
		if (ExcelFileParser.hasExcelFormat(file)) {
				List<UserAccount> studentUserAccounts = excelFileHelperService.saveToDatabase(file);
//				List<UserAccount> studentUserAccounts = userAcctService.getStudentUserAccountforRegistration();
				emailService.sendEmail(studentUserAccounts);
				message = "Email Send successfully";
		}else {
			message = "Please upload an excel file!";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	}
	
	@GetMapping("/guides")
	public ResponseEntity<?> getGuideList(){
		List<Guide> guideList = adminService.getGuideList();
		if (guideList.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(guideList, HttpStatus.OK);
	}
	
	@PostMapping("/guides/register") 
	ResponseEntity<?> registerGuides(@RequestBody UserAccount guideUser, 
			@RequestBody List<String> technologies // TODO Once again confirm if the request body will contain this
			){
		UserAccount registerUser = userAcctService.registerUser(guideUser);
		Guide guide = new Guide();
		List<Technology> technologyDbList = technologyService.getAllTechnology(); 
		guide.setUser(registerUser);
		for(String technology: technologies) {
			// TODO uppercase confirm upper case of string from front end
			for(Technology tobj:technologyDbList) {
				if(technology.equals(tobj.getName()) ) {
					guide.getTechnologies().add(tobj);
					tobj.getGuides().add(guide);
					technologyService.saveTechnology(tobj);
					break;
				}		
			}
		}	
		guide = guideService.registerGuide(guide);
		ResponseMessage response = new ResponseMessage("Guide "+guide.getUser().getFirstName()+" registered Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/projects/list")
	public ResponseEntity<?> getProjectList(){
		List<Project> projectList = projectService.getAllProjectList();
		if (projectList.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(projectList, HttpStatus.OK);
	}
	
	@GetMapping("/{userid}/teamsize")
	public ResponseEntity<?> getTeamsize(@PathVariable Integer userid) {
		Admin adminAccount = adminService.findByUserAccount(new UserAccount(userid));
		return new ResponseEntity<> (adminAccount.getProjectMinSize(), HttpStatus.OK);
	}
	
	@PostMapping("/{userid}/setsize") //TODO convey the request parameter name
	public ResponseEntity<?> setTeamSize(@PathVariable Integer userid, @RequestParam(name = "size") int projectMinSize){
		Admin adminAccount = adminService.findByUserAccount(new UserAccount(userid));
		if(adminAccount.getProjectMinSize() != projectMinSize) {
			adminAccount.setProjectMinSize(projectMinSize);
			return new ResponseEntity<> (adminService.save(adminAccount), HttpStatus.OK);
		}
		return new ResponseEntity<> (adminAccount, HttpStatus.CONFLICT);
	}
}
