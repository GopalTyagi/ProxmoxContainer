package com.tech.task.entities;

import java.util.ArrayList;
import java.util.List;

import com.tech.task.dto.StudentDTO;
import com.tech.task.dto.SubjectDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Subjects-Table")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subject_id")
	private int subjectId;

	@Column(name = "subject_name")
	private String subjectName;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student; 

	@Transient
	private List<SubjectDTO> list = new ArrayList<>();

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<SubjectDTO> getList() {
		return list;
	}

	public void setList(List<SubjectDTO> list) {
		this.list = list;
	}

}