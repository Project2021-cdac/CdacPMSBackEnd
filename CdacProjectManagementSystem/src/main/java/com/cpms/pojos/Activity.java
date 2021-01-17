package com.cpms.pojos;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author dev2000
 *	Activity Class is used to log activities.
 *	Activities like Task Creation, Task Completion, Task updation, Guide session start, Guide session end etc.
 *	
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity_table")
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	private Timestamp createdOn;
	@Column(length = 300)
	private String activityDesription;
	private HashMap<Status, ArrayList<String>> list=new HashMap<Status,ArrayList<String>>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(columnDefinition="integer", name="project_id")
	private Project projectId;
	
	@Override
	public String toString() {
		return "Activity [activityId=" + id + ", createdOn=" + createdOn + ", activityDesription="
				+ activityDesription + ", list=" + list + "]";
	}
}
