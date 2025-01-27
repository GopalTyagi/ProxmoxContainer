package com.tech.task.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String string, String string2) {
		super("Resource Not Found on Server .....!!");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}