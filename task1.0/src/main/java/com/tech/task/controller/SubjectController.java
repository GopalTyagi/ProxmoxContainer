package com.tech.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.task.entity.Subject;
import com.tech.task.repo.SubjectRepository;
import com.tech.task.service.SubjectService;

@RestController
@RequestMapping("/subject")
public class SubjectController {
	
	@Autowired
	private SubjectService service;
	
	@Autowired
	private SubjectRepository subjectRepo;
	
	
	@PostMapping("/save")
	public ResponseEntity<Subject> saveSubjects(@RequestBody Subject subject){
		System.out.println("subject = ");
		Subject subjectDetails= service.saveSubjectDetails(subject);
		return new ResponseEntity<>(subjectDetails,HttpStatus.CREATED);
		
	}
	
	
	@PostMapping("/students/{studentId}/subjects")
	public ResponseEntity<List<Subject>> saveSubjectsForStudent(@PathVariable int studentId,@RequestBody List<Subject> subjects) {
	    List<Subject> savedSubjects = service.saveSubjectsForStudent(studentId, subjects);
	    return new ResponseEntity<>(savedSubjects, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	 public void  deleteStudentById(@PathVariable("id") int id) {
	  subjectRepo.deleteBySubjectId(id);
	 }
	
}
