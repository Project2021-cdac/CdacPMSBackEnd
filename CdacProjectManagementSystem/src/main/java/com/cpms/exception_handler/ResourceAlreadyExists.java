package com.cpms.exception_handler;

@SuppressWarnings("serial")
public class ResourceAlreadyExists extends RuntimeException {

    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
