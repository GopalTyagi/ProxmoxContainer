package com.fit.fitness;

import java.util.HashMap;
import java.util.Map;

import com.fit.fitness.entity.User;

public class AppResponse {

	public static String DATA = "data";
	public static String INPUT_ERROR = "inputerror";
	public static String MESSAGE = "message";

	private boolean success = false;

	private Map<String, Object> result = new HashMap<String, Object>();

	private String jwttoken;

	public AppResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public AppResponse() {
	}

	public AppResponse(boolean success) {
		this.success = success;
	}

	public AppResponse(boolean success, String message) {
		this.success = success;
		addMessage(message);
	}

	public AppResponse(boolean success, String message, Object value) {
		this.success = success;
		addMessage(message);
		addData(value);
		System.out.println("........");
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public void addResult(String key, Object value) {
		result.put(key, value);
	}

	public void addData(Object value) {
		result.put(DATA, value);
	}

	public void addInputErrors(Object value) {
		result.put(INPUT_ERROR, value);
	}

	public void addMessage(Object value) {
		result.put(MESSAGE, value);
	}

}