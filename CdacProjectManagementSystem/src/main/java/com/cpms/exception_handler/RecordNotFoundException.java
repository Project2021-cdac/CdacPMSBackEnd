package com.cpms.exception_handler;

@SuppressWarnings("serial")
public class RecordNotFoundException extends RuntimeException {
	
	public RecordNotFoundException(String mesg) {
		super(mesg);
	}
}
