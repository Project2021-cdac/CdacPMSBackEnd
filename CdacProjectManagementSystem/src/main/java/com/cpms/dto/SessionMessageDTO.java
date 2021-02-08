package com.cpms.dto;

import com.cpms.pojos.Project;
import com.cpms.pojos.Session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
@ToString
public class SessionMessageDTO {
	private Session session;
	private Project project;
	private String message;
}
