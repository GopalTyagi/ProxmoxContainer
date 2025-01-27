package com.tech.task.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.task.DTO.StudentMarksDTO;
import com.tech.task.Exception.ResourceNotFoundException;
import com.tech.task.entity.Student;
import com.tech.task.entity.Subject;
import com.tech.task.entity.SubjectDetailsWithStudent;
import com.tech.task.repo.MarksRepository;
import com.tech.task.repo.StudentRepository;
import com.tech.task.repo.SubjectRepository;

@Service
public class SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	private MarksRepository marksRepo;
	
	public Subject saveSubjectDetails(Subject subject) {
		return subjectRepo.save(subject);
	}
	
	
//	public Subject findById(int id) {
//        return subjectRepo.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));
//    }
	
	public List<Subject> saveSubjectsForStudent(int studentId, List<Subject> subjects) {
	    // Log the student ID being searched
	    System.out.println("Searching for student with ID: " + studentId);
	    
	    // Find the student by ID
	    Student student = studentRepo.findById(studentId)
	        .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

	    List<Subject> savedSubjects = new ArrayList<>();
	    for (Subject subject : subjects) {
	        subject.setStudent(student); // Associate the student with the subject
	        Subject savedSubject = subjectRepo.save(subject); // Save the subject
	        savedSubjects.add(savedSubject); // Add the saved subject to the list
	    }
	    
	    return savedSubjects; // Return the list of saved subjects
	}

	public List<SubjectDetailsWithStudent> saveMarksForStudent(StudentMarksDTO studentMarksDTO) {
	    // Find the student by ID
	    Student student = studentRepo.findById(studentMarksDTO.getStudentId())
	            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentMarksDTO.getStudentId()));

	    List<SubjectDetailsWithStudent> savedMarks = new ArrayList<>();

	    // Iterate through the subject marks and save them
	    for (StudentMarksDTO.SubjectMarks subjectMarks : studentMarksDTO.getSubjectMarks()) {
	        // Find the subject by subjectName
	        Subject subject = subjectRepo.findBySubjectName(subjectMarks.getSubjectName())
	                .orElseThrow(() -> new RuntimeException("Subject not found with name: " + subjectMarks.getSubjectName()));

	        SubjectDetailsWithStudent mapping = new SubjectDetailsWithStudent();
	        mapping.setStudent(student);
	        mapping.setSubject(subject);
	        mapping.setMarks(subjectMarks.getMarks());

	        // Save the mapping in the repository
	        savedMarks.add(marksRepo.save(mapping));
	    }

	    return savedMarks; // Return the list of saved marks
	}
	
}
