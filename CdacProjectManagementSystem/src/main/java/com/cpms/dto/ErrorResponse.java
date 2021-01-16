package com.cpms.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private String message, detailedMessage;
	private LocalDateTime dateTime;
	
	public ErrorResponse(String message, String details) {
		this.message = message;
		this .detailedMessage = details;
	}

	@Override
	public String toString() {
		return "ErrorResponse [Message =" + message + ", \ndetailedMessage =" + detailedMessage + ", \nDate_Time =" + dateTime
				+ "]";
	}

	
	
}
