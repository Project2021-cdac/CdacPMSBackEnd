	package com.cpms.pojos;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dev2000 Guide Class stores basic details of guide. Many projects can
 *         be lead by One Guide. Multiple technologies can be professed by Many
 *         Guide.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "guide_table")
public class Guide {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private boolean inSession;

	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private UserAccount userAccount;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "guide_technology_table", joinColumns = @JoinColumn(name = "guide_id"), inverseJoinColumns = @JoinColumn(name = "technology_id"))
//	@JsonIgnoreProperties("guides")
	@JsonSerialize(as = HashSet.class)
	private Set<Technology> technologies = new HashSet<>();

	public Guide(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Guide [id= " + id + ", inSession= " + inSession + ", user= " + userAccount + "]";
	}

	@JsonIgnore
	public Set<Technology> getTechnologies() {
		return this.technologies;
	}

}
