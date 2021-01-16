package com.cpms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author karun
 *
 */

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProjectDTO {

	private String projectTitle;
	private String projectDescription;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long teamLead;
	private List<Integer> technologies;
	private List<Long> teamPrns;
}
