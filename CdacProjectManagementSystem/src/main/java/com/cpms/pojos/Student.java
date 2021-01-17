package com.cpms.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dev2000 Student Class stores basic details of student. Many Students
 *         group for one project
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_table")
public class Student {
	@Id
	@Column(name = "prn", updatable = true)
	private Long prn;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserAccount userAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	public Student(Long prn) {
		this.prn = prn;
	}

	// to avoid recursion during serialization
	@JsonIgnore
	public Project getProject() {
		return project;
	}

}
