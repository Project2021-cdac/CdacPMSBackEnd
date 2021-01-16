package com.cpms.pojos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author dev2000
 *	Task Class is used by students to add/create a task for managing the project flow according to current running milestones
 */

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_table")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Status status;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdOn;
	
	/**
	 * 	PRN of Student who created the Task.
	 */
	private Long createdBy;				
	@NotBlank(message = "Task Description can't be blank")
	@Size(min = 5, max = 150, message = "Task Description must be between 5 and 150 characters")
	@Column(length = 150)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="milestone_id")
	private Milestone milestone;
	
	@Override
	public String toString() {
		return "Task [Task Id=" + id + ", milestone=" + milestone + ", status=" + status + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + ", description=" + description + "]";
	}
}
