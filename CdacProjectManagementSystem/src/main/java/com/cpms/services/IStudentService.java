package com.cpms.services;

import java.util.List;
import java.util.Optional;

import com.cpms.dto.ProjectDTO;
import com.cpms.dto.ProjectStatusDTO;
import com.cpms.dto.ProjectStudentResponseDTO;
import com.cpms.pojos.Activity;
import com.cpms.pojos.Course;
import com.cpms.pojos.Project;
import com.cpms.pojos.Student;
import com.cpms.pojos.Task;
import com.cpms.pojos.UserAccount;

public interface IStudentService {
	public Optional<Student> getStudentByUserAccount(UserAccount userAccount);
	public List<Student> getStudentsWithoutProject(Course course);
	ProjectStudentResponseDTO registerProject(ProjectDTO projectDTO);
	Activity saveProjectCreationActivity(Project project);
	List<ProjectStatusDTO> getProjectMilstonesAndTaskdetails(int projId);
	List<Student> getStudentListByProject(Project project);
	Task createTask(Task newtask);
	Optional<Task> getTask(Integer id);
	List<Task> getTasksofStudent(Student student);
	Optional<Student> getStudentByPRN(Long studentId);
	List<Student> getAllStudents();
}
