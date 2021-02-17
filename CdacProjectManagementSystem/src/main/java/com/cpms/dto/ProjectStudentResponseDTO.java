package com.cpms.dto;

import java.util.List;

import com.cpms.pojos.Project;
import com.cpms.pojos.Student;

public class ProjectStudentResponseDTO {
	
	private Project project;
	private List <Student> students;
	
	
	public ProjectStudentResponseDTO(Project project, List<Student> students) {
		super();
		this.project = project;
		this.students = students;
	}
	
	public Project getProject() {
		return project;
	}
	public void ListProject(Project project) {
		this.project = project;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void ListStudents(List<Student> students) {
		this.students = students;
	}
	
	
}
