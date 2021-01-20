package com.cpms.pojos;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author dev2000
 *	Activity Class is used to log activities.
 *	Activities like Task Creation, Task Completion, Task updation, Guide session start, Guide session end etc.
 *	
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "activity_table")
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	private Timestamp createdOn;
	@Column(length = 300)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name="project_id")
	private Project projectId;
	
	@Override
	public String toString() {
		return "Activity [activityId=" + id + ", createdOn=" + createdOn + ", activityDesription="
				+ description;
	}

	public Activity(Timestamp createdOn, String description, Project project) {
		this.createdOn = createdOn;
		this.description = description;
		this.projectId = project;
	}
	
	@JsonIgnore
	public Project getProjectId() {
		return this.projectId;
	}
}
