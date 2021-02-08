package com.cpms;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.AdminRepository;
import com.cpms.repository.GuideRepository;
import com.cpms.repository.ProjectRepository;
import com.cpms.repository.StudentRepository;
import com.cpms.repository.UserAccountRepository;
import com.cpms.services.IAdminService;
import com.cpms.services.IGuideService;
import com.cpms.services.IProjectService;
import com.cpms.services.IUserAccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTests {

	@Autowired
	private IAdminService admservice; 
	
	@Autowired
	private IUserAccountService usracctService;
	
	@Autowired
	private IGuideService guideService;

	@Autowired
	private IProjectService projService;

	@Autowired
	@MockBean
	private AdminRepository admrepo;
	
	@Autowired
	@MockBean 
	private StudentRepository studrepo;
	
	@Autowired
	@MockBean 
	private GuideRepository guiderepo;
	 
	@Autowired
	@MockBean 
	private UserAccountRepository  usracctrepo;
	
	@Autowired
	@MockBean
	private ProjectRepository projRepo;
	
	@Test
	public void getStudentListOrderedByPRNTest() {
		Course courses = Course.DAC;
		when(studrepo.findAllByOrderByPrn(courses))
		.thenReturn(Stream.of(
					new Student(200240120072L),
					new Student(200240120074L)
		).collect(Collectors.toList()) );
		assertEquals(2, admservice.getStudentListOrderedByPrn(Course.DAC).size());
	}
	
	@Test
	public void getGuideListTest() {
		when(guiderepo.findByCourse(Course.DAC)).
			thenReturn(Stream.of(
					new Guide(1),
					new Guide(2)
					).collect(Collectors.toList()) ) ;
		assertEquals(2, guideService.getGuideList(Course.DAC).size());	
	}
	
	@Test
	public void registerUserTest() {
		UserAccount user = new UserAccount(1);
		when(usracctrepo.save(user)).
			thenReturn(user) ;
		assertEquals(user, usracctService.registerUser(user));	
	}
	
	@Test
	public void getProjectListTest() {
		when(projRepo.findAllByOrderById(Course.DAC)).
		thenReturn(Stream.of(
				new Project(2),
				new Project(3)
				).collect(Collectors.toList()) );
		assertEquals(2, projService.getAllProjectList(Course.DAC).size());	
	}
	
}
