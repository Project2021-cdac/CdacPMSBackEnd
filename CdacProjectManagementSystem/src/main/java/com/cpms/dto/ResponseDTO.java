package com.cpms.dto;

import java.time.LocalDateTime;

public class ResponseDTO {
	private String mesg;
	private LocalDateTime ts;

	public ResponseDTO() {
		
	}

	public ResponseDTO(String mesg) {
		super();
		this.mesg = mesg;
		this.ts = LocalDateTime.now();
	}

	public String getMesg() {
		return mesg;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}

	public LocalDateTime getTs() {
		return ts;
	}

	public void setTs(LocalDateTime ts) {
		this.ts = ts;
	}

}
