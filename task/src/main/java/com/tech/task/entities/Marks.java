package com.tech.task.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Marks-Table")
public class Marks{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mapping_id",length = 100,nullable = false)
	private int mappingId;
	
    @ManyToOne()
	@JoinColumn(name="studentId")
	private Student student;
	
    @ManyToOne()
	@JoinColumn(name="subjectId")
	private Subject subject;
	
	@Column(name="marks")
	private int marks;


	public int getMappingId() {
		return mappingId;
	}


	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public int getMarks() {
		return marks;
	}


	public void setMarks(int marks) {
		this.marks = marks;
	}
	
	

}