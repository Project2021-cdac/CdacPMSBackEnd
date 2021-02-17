package com.cpms.exception_handler;

@SuppressWarnings("serial")
public class CustomException extends RuntimeException {

    public CustomException(String message) {
    	super(message); 
    }

}
