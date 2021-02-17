package com.cpms.dto;

import com.cpms.pojos.Session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrentSessionDTO {
	Session currentSession;
	Integer projectId;
}
