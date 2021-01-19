package com.cpms.services;

import java.util.List;

import com.cpms.dto.ProjectDTO;
import com.cpms.pojos.Activity;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.UserAccount;

public interface IStudentService {
	public Student getStudentByUserAccount(UserAccount userAccount);
	public List<Student> getStudentsWithoutProject();
	Project registerProject(ProjectDTO projectDTO);
	Activity saveProjectCreationActivity(Project project);
}
