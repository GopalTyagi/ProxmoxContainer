package com.tech.task.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Ensure this is set
	@Column(name = "student_id", nullable = false) // Ensure nullable is set to false
	private int studentId;

	@Column(name = "student_name", nullable = false) // Make this NOT NULL
	private String name;

	@Column(name = "email_id", unique = true, nullable = false) // Make this NOT NULL
	private String email;

	@Column(name = "password", unique = true, nullable = false) // Make this NOT NULL
	private String password;

	// Constructors, Getters, and Setters
	public Student() {
	}

	public int getStudentId() {
		return studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}