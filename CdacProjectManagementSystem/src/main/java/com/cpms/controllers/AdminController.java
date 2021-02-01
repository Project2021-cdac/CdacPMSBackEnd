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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.dto.RegisterGuideDTO;
import com.cpms.fileutils.excelfilehelper.ExcelFileParser;
import com.cpms.pojos.Admin;
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
import com.cpms.services.ITechnologyService;
import com.cpms.services.IUserAccountService;

@CrossOrigin(origins = "*")
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
	
	
	@GetMapping(value = "/students/:coursename")
	public ResponseEntity<?> getStudentList() {
		List<Student> studentListOrderedByPrn = adminService.getStudentListOrderedByPrn();
		if (studentListOrderedByPrn.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(studentListOrderedByPrn, HttpStatus.OK);
	}

	//TODO how to avoid multiple registeration. FrontEnd Perhaps?
	@PostMapping(value = "/students/register")
	public ResponseEntity<?> registerStudent(@RequestBody MultipartFile file) throws Exception {
		System.out.println(file.isEmpty());
		if (ExcelFileParser.hasExcelFormat(file)) {
				List<UserAccount> studentUserAccounts = excelFileHelperService.saveToDatabase(file);
				emailService.sendEmail(studentUserAccounts);
		}else {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload an ExcelFile!!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("All Students registered successfully");
	}
	
	// TODO Complete List of data
	// TODO remaining to test
	@GetMapping(value = "/guides")
	public ResponseEntity<?> getGuideList(){
		List<Guide> guideList = guideService.getGuideList();
		if (guideList.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(guideList, HttpStatus.OK);
	}
	
	//TODO Convey to front end this info
	@PostMapping(value = "/guides/register/:coursename") 
	ResponseEntity<?> registerGuides(@RequestBody RegisterGuideDTO guideUser){
//		System.out.println(guideUsers);
		if(null != guideUser || !guideUser.getTechnologylist().isEmpty()) {
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
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}
	//TODO remaining to test
	@GetMapping(value = "/projects/list/:coursename")
	public ResponseEntity<?> getProjectList(){
		List<Project> projectList = projectService.getAllProjectList();
		if (projectList.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(projectList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{userid}/teamsize/:coursename")
	public ResponseEntity<?> getTeamsize(@PathVariable int userid) {
		Optional<Admin> adminAcct = adminService.getAdminByUserAccount(new UserAccount(userid));
		if(adminAcct.isPresent()) {
			return new ResponseEntity<> (adminAcct.get().getProjectMinSize(), HttpStatus.OK);
		}
		return new ResponseEntity<> (HttpStatus.NO_CONTENT);

	}
	
	@PutMapping(value = "/{userid}/setsize") 
	public ResponseEntity<?> setTeamSize(@PathVariable Integer userid, @RequestParam(name = "size") int projectMinSize){
		Optional<Admin> adminAcct = adminService.getAdminByUserAccount(new UserAccount(userid));
		if(adminAcct.isPresent()){
			Admin admin = adminAcct.get();
			if(admin.getProjectMinSize() != projectMinSize) {
				admin.setProjectMinSize(projectMinSize);
				return new ResponseEntity<> (adminService.save(admin).getProjectMinSize(), HttpStatus.OK);
			}
			return new ResponseEntity<> (adminAcct, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
