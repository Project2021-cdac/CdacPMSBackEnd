package com.cpms.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dev2000 Student Class stores basic details of student. Many Students
 *         group for one project
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student_table")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="prn")
public class Student {
	@Id
	@Column(name = "prn", updatable = true)
	private Long prn;

	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private UserAccount userAccount;

	@ManyToOne()
	@JoinColumn( name = "project_id")
	private Project project;

	public Student(Long prn) {
		this.prn = prn;
	}
	
	public Project getProject() {
		return project;
	}

}
