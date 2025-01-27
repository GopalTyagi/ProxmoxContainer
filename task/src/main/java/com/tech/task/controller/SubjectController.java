package com.tech.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tech.task.entities.Subject;
import com.tech.task.service.SubjectSevice;

@RestController
@RequestMapping("/home/subject")
public class SubjectController {

	@Autowired
	private SubjectSevice service;

	@PostMapping("/add")
	public ResponseEntity<Subject> saveSubjects(@RequestBody Subject subject) {
		System.out.println("subject = ");
		Subject subjectDetails = service.saveSubjectDetails(subject);
		return new ResponseEntity<>(subjectDetails, HttpStatus.CREATED);

	}

}