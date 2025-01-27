package com.tech.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.task.entities.Marks;
import com.tech.task.entities.Student;
import com.tech.task.exception.ResourceNotFoundException;
import com.tech.task.repositories.MarksRepository;
import com.tech.task.repositories.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private MarksRepository marksRepo;

	@Autowired
	private PasswordEncoder encoder;

	public Student saveStudent(Student student) {
		student.setPassword(encoder.encode(student.getPassword()));
		return studentRepo.save(student);
	}

	public Student getStudentById(int studentId) {
		return studentRepo.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));
	}

	public List<Marks> getStudentMarksById(int studentId) {
		return marksRepo.findMarksByStudent_studentId(studentId);
	}

}
