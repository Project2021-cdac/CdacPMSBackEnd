package com.cpms.pojos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * @author dev2000
 *	 Project Class having basic details regarding individual Project Team
 *	 Multiple technologies can be used in one Project.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project_table")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Integer id;

	@NotBlank(message = "Project Title can't be blank")
	@Size(max = 100, message = "Project Title must be less than 100 characters")
	@Column(length = 100)
	private String title;

	@NotBlank(message = "Project Description can't be blank")
	@Size(min = 10, max = 300, message = "Project Description must be between 10 and 300 characters")
	@Column(length = 300)
	private String description;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@Future(message = "Project End Date must be in future")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	@Min(0)							// TODO - Method to calculate project progress acc to tasks -  
	@Max(100)						//		Math.floor((total tasks/ total compleated task)*100)
	private int progress;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "teamlead_prn")
	private Student teamLead;

	@ManyToOne()
	@JoinColumn(name = "guide_id")
	private Guide guide;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "project_technology_table", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "technology_id"))
	@JsonIgnoreProperties("projects")
	private Set<Technology> technologies = new HashSet<>();

	public Project(
			@NotBlank(message = "Project Title can't be blank") @Size(max = 100, message = "Project Title must be less than 100 characters") String title,
			@NotBlank(message = "Project Description can't be blank") @Size(min = 30, max = 300, message = "Project Description must be between 10 and 300 characters") String description,
			LocalDate startDate, @Future(message = "Project End Date must be in future") LocalDate endDate,
			Student teamLead) {
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.teamLead = teamLead;
	}

	@Override
	public String toString() {
		return "Project [Project Id=" + id + ", title=" + title + ", description=" + description + ", startDate="
				+ startDate + ", endDate=" + endDate + ", guide=" + guide + "]";
	}

	// helper method
	public void addTechnology(Technology technology) {
		this.technologies.add(technology);
		/* technology.addProject(this); */
		technology.getProjects().add(this);
	}
	
	
	@JsonIgnore
	public Set<Technology> getTechnologies() {
		return this.technologies;
	}
}
