package com.cpms.dto;

import java.util.List;

import com.cpms.pojos.Milestone;
import com.cpms.pojos.Task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectStatusDTO {
	private Milestone milestone;
	private List<Task> tasklist;
	
	public ProjectStatusDTO(Milestone milestone, List<Task> tasklist) {
		this.milestone=milestone;
		this.tasklist = tasklist;
	}
}
