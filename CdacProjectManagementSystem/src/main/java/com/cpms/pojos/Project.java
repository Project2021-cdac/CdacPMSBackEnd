package com.cpms.pojos;

import java.time.LocalDate;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dev2000
 *	 Project Class having basic details regarding individual Project Team
 *	 Multiple technologies can be used in one Project.
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "project_table")
public class Project {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name= "project_id", updatable=true, unique=true, nullable=false)
	private Integer projectId;
	
	@NotBlank(message = "Project Title can't be blank")
	@Size(max = 100, message = "Project Title must be less than 100 characters")
	@Column(length = 100)
	private String projectTitle;
	
	@NotBlank(message = "Project Description can't be blank")
	@Size(min = 30, max = 300, message = "Project Description must be between 10 and 300 characters")
	@Column(length = 300)
	private String projectDescription;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;		
	
	@Future(message = "Project End Date must be in future")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	@OneToOne
	@JoinColumn(name = "teamlead_prn")
	private Student teamLead;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "guide_id")
	private Guide guide;
	
//	@OneToMany(mappedBy = "technologyId", orphanRemoval = true)
//	@JoinColumn(name = "technology_id")
//	private List<Technology> technologies;
	
//	@OneToMany(mappedBy = "milestoneId", orphanRemoval = true)
//	@JoinColumn(name = "milestone_id")
//	private List<Milestone> milestones;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "project_technology_table", 
	joinColumns = @JoinColumn(name = "project_id"),
	inverseJoinColumns = @JoinColumn(name = "technology_id"))
	@JsonIgnoreProperties("projects")
	private Set<Technology> technologies;

	/**
	 *	Method to display each class field according to our requirement 
	 */
	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", projectTitle=" + projectTitle + ", projectDescription="
				+ projectDescription + ", startDate=" + startDate + ", endDate=" + endDate + ", guide=" + guide + "]";
	}
	

}


