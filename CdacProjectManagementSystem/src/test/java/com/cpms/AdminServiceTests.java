package com.cpms;

import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cpms.pojos.Course;
import com.cpms.pojos.Project;
import com.cpms.pojos.Role;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.AdminRepository;
import com.cpms.repository.StudentRepository;
import com.cpms.services.IAdminService;
import com.sun.el.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTests {

	@Autowired
	private IAdminService admservice; 
	
	@MockBean
	private AdminRepository admrepo;
	
	@Autowired StudentRepository studrepo;
	private 
	
	@Test
	public void getStudentListOrderedByPRNTest(Course course) {
		when( studrepo.findAllByOrderByPrn(course))
		.thenReturn(Stream.
				of(new Student(200240120077L, new UserAccount(1, "Joji", "Joy", "joji@gmail.com", "1234", "9876543210","2020-01-1993", 
						Role.ROLE_STUDENT, course),new Project(1)),
					new Student(200240120077L, new UserAccount(2, "Joji", "Joy", "joji@gmail.com", "1234", "9871543210","2020-01-1993", 
								Role.ROLE_STUDENT, course),new Project(1))
						).collect(Collectors.toList()) );
	}
}
