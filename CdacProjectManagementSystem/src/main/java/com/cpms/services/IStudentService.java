package com.cpms.services;

import java.util.List;

import com.cpms.dto.ProjectDTO;
import com.cpms.dto.ProjectStatusDTO;
import com.cpms.pojos.Milestone;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.Task;
import com.cpms.pojos.UserAccount;

public interface IStudentService {
	Student getStudentByUserAccount(UserAccount userAccount);
	List<Student> getStudentsWithoutProject();
	Project registerProject(ProjectDTO projectDTO);
	Task createTask(Task newtask);
	List<ProjectStatusDTO> getProjectMilstonesAndTaskdetails(Integer projId);
}
