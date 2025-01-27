package com.tech.task.Exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4169528211739042556L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
