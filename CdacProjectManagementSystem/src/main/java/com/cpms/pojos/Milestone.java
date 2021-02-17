package com.cpms.pojos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	private String title;
	private String description;
	
	public Milestone(Integer milestoneid) {
		this.id=milestoneid;
	}
}
