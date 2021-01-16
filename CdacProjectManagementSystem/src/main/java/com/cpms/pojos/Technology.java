package com.cpms.pojos;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dev2000
 *	Technology Class has list of Technology Names and its relation with 
 *	the classes- Student Class and Guide Class which utilizes it. 
 *	Many To Many relation with Student and Guide Class
 */

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "technology_table")
public class Technology {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "technology_id")
	private Integer id;
	
	@Column(length=50)
	private String name;
	
//	@Enumerated(EnumType.STRING)
//	private TechnologyName technologyName;
//	
	@ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "technologies")
	@JsonIgnoreProperties("technologies")
	private Set<Guide> guides;
	
	@ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "technologies")
	@JsonIgnoreProperties("technologies")
	private Set<Project> projects;
	
	@Override
	public String toString() {
		return "Technology [Technology Id=" + id + ", Name=" + name + "]";
	}
	
	//helper method
	public void addProject(Project project) {
		this.projects.add(project);	
	}

}
