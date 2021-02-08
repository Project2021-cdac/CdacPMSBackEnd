package com.cpms.dto;

import java.util.List;

import com.cpms.pojos.Activity;
import com.cpms.pojos.Milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GuideProjectDTO {
	//private Project project;
	private List<Activity> activities;
	private List<Milestone> milestones;
	private List<String> studentNames;
}


