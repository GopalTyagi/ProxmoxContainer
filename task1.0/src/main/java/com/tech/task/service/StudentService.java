package com.tech.task.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tech.task.DTO.StudentMarksDTO;
import com.tech.task.Exception.ResourceNotFoundException;
import com.tech.task.entity.Student;
import com.tech.task.entity.SubjectDetailsWithStudent;
import com.tech.task.repo.MarksRepository;
import com.tech.task.repo.StudentRepository;


@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private MarksRepository marksRepo;
	
	public Student saveStudentInDb(Student student) {
		return studentRepo.save(student);
	}
	
	public Student getStudentById(int studentId) {
        return studentRepo.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));
        }
	
	public List<SubjectDetailsWithStudent>  getStudentDetailsWithMarksById(int studentId){
		return marksRepo.findMarksByStudentId(studentId);
	}

    // Method to get all students
    public List<Student> findAllStudentsDetails() {
        return studentRepo.findAll();
    }


}
	


