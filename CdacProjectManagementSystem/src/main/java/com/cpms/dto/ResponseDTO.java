package com.cpms.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDTO {
	private String message;
	private LocalDateTime ts;

	public ResponseDTO(String message) {
		super();
		this.message = message;
		this.ts = LocalDateTime.now();
	}

}
