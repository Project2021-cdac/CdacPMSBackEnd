package com.cpms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.dto.ResponseMessage;
import com.cpms.fileutils.excelfilehelper.ExcelFileParser;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Student;
import com.cpms.pojos.Technology;
import com.cpms.pojos.UserAccount;
import com.cpms.services.IAdminServices;
import com.cpms.services.IEmailService;
import com.cpms.services.IExcelFileHelperService;
import com.cpms.services.IGuideService;
import com.cpms.services.ITechnologyService;
import com.cpms.services.IUserAccountService;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

//	private static List<Technology> technologylist;
	
	@Autowired
	private IAdminServices adminService;

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
	
	
	@GetMapping("/students")
	public ResponseEntity<?> getStudentList() {
		List<Student> studentListOrderedByPrn = adminService.getStudentListOrderedByPrn();
		if (studentListOrderedByPrn.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(studentListOrderedByPrn, HttpStatus.OK);
	}

	@PostMapping("/students/register")
	public ResponseEntity<?> registerStudent(@RequestParam(name = "file") MultipartFile file) {
		String message = null;
		if (ExcelFileParser.hasExcelFormat(file)) {
			try {
				excelFileHelperService.save(file);
				List<UserAccount> studetUserAccounts = userAcctService.getStudentUserAccountforRegisteration();
				emailService.sendEmail(studetUserAccounts);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
//		        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
//		    	e.printStackTrace();
//		        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//		        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}
//		    message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}
	
	@GetMapping("/guides")
	public ResponseEntity<?> getGuideList(){
		List<Guide> guideList = adminService.getGuideList();
		if (guideList.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(guideList, HttpStatus.OK);
	}
	
	@PostMapping("/guides/register") 
	ResponseEntity<?> registerGuides(@RequestBody UserAccount guideuser, 
			@RequestBody List<String> technologies){
		UserAccount registerUser = userAcctService.registerUser(guideuser);
		Guide guide = new Guide();
		List<Technology> technologyDbList = technologyService.getAllTechnology(); 
		guide.setUser(registerUser);
		for(String technology: technologies) {
				//uppercase?
			for(Technology tobj:technologyDbList) {
				if(technology.equals( tobj.getTechnologyName().name()) ) {
					guide.getTechnologies().add(tobj);
					tobj.getGuides().add(guide);
					break;
				}		
			}
		}	
		guideService.registerGuide(guide);
		ResponseMessage response = new ResponseMessage("Guide Registered Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
