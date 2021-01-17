package com.cpms.pojos;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author dev2000
 *	Milestones are pre-provided and these milestone must be completed for successful completion of Project.
 *	Every Milestone has start date and end date. 
 *	Before completion of one milestone, next milestone can't be started.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "milestone_table")
public class Milestone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	private MilestoneCheckpoint milestoneCheckpoint;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer", name="project_id")
	private Project projectId;
	
	@Override
	public String toString() {
		return "Milestone [milestoneId=" + id + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", mileStoneCheckPoint=" + milestoneCheckpoint + "]";
	}
}
