package com.cpms.dto;

import java.time.LocalDate;
import java.util.List;

import com.cpms.pojos.Project;
import com.cpms.pojos.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/**
 * A class which wraps around various project parameters needed to save project
 * details as well as the students who are to be linked to that particular
 * project instance
 */

@ToString
public class ProjectDTO {
	private String projectTitle;
	private String projectDescription;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long teamLead;
	private List<Integer> technologies;
	private List<Long> studentPrns;

	public Project getProject() {
		return new Project(projectTitle, projectDescription, startDate, endDate, new Student(teamLead));
	}
}
