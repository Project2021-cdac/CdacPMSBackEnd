package com.cpms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import com.cpms.pojos.Course;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;
import com.cpms.repository.ProjectRepository;
import com.cpms.repository.StudentRepository;
import com.cpms.services.IProjectService;
import com.cpms.services.IStudentService;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentServiceTests {

	@Autowired
	private IProjectService projectService;
	@Autowired
	private IStudentService studentService;

	@Autowired
	@MockBean
	private StudentRepository studentRepository;

	@Autowired
	@MockBean
	private ProjectRepository projectRepository;

	@Test
	public void saveProjectTest() {
		Project project = new Project(3);
		when(projectRepository.save(project)).thenReturn(project);
		assertEquals(project, projectService.registerProject(project));
	}

	@Test
	public void getStudentsTest() {
		List<Student> students = new ArrayList<>();
		students.add(new Student(Long.valueOf("200240120080")));
		students.add(new Student(Long.valueOf("200240120090")));
		
		when(studentRepository.findAll()).thenReturn(students);
		assertEquals(2, studentService.getAllStudents().size());
	}
	
	@Test
	public void getStudentByUserAccountTest() {
		UserAccount userAccount = new UserAccount(12);
		
		when(studentRepository.findByUserAccount(userAccount)).thenReturn(Optional.of(new Student(Long.valueOf("200230120080"))));
		assertEquals(true, studentService.getStudentByUserAccount(userAccount).isPresent());
	}
	
	@Test
	public void getStudentsWithoutProjectTest() {
		Course courseName = Course.DAC;
		List<Student> students = new ArrayList<>();
		students.add(new Student(Long.valueOf("200240120080")));
		students.add(new Student(Long.valueOf("200240120090")));
		
		when(studentRepository.findStudentsWithoutProject(courseName)).thenReturn(students);
		assertEquals(2, studentService.getStudentsWithoutProject(courseName).size());
	}
}
