package com.tech.task.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.task.entities.Marks;
import com.tech.task.service.MarksService;

@RestController
@RequestMapping("/home/marks")
public class MarksController {

	@Autowired
	private MarksService marksService;

	@PostMapping("/add")
	public ResponseEntity<Marks> addMarks(@RequestBody Marks marks) {
		Marks marks2 = marksService.addMarksForStudent(marks);
		return new ResponseEntity<>(marks2, HttpStatus.CREATED);
	}

}