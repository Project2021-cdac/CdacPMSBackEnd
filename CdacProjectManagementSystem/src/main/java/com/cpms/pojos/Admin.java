package com.cpms.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dev2000
 *	Admin has highest access rights.
 *	Admin only can generate Student and Guide Login Credentials for first time.(Registration)
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="admin_table")
public class Admin{
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	@Column(updatable = true)
	private Integer id;
	
	@Column(length = 10,  columnDefinition = "int default 4")
	private int projectMinSize;
	
	@OneToOne(orphanRemoval = true)
	@JoinColumn(name="user_id")
	private UserAccount userAccount;	
	
	public Admin(Integer id) {
		this.id = id;
	}
}
