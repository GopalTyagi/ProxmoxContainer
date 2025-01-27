package com.tech.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.task.DTO.StudentMarksDTO;
import com.tech.task.entity.SubjectDetailsWithStudent;
import com.tech.task.service.MarksService;

@RestController
@RequestMapping("/marks")
public class MarksController {
	
	@Autowired
	private MarksService marksService;
	
	
    @PostMapping("/saveAllMarks")
    public ResponseEntity<List<SubjectDetailsWithStudent>> saveMarksForStudent(@RequestBody StudentMarksDTO studentMarksDTO) {
        List<SubjectDetailsWithStudent> savedMarks = marksService.saveMarksForStudent(studentMarksDTO);
        return new ResponseEntity<>(savedMarks, HttpStatus.CREATED);
    }
	
//	@PostMapping("/saveMarks")
//	public ResponseEntity<SubjectDetailsWithStudent> saveStudentMarks(@RequestBody SubjectDetailsWithStudent subjectDetailsWithStudent,
//			Student student,Subject subject){
//		SubjectDetailsWithStudent subjectDetailsWithStudentDetails = marksService.saveMarksForStudent(subjectDetailsWithStudent);
//		return new ResponseEntity<>(subjectDetailsWithStudentDetails,HttpStatus.CREATED);
//	}

	
    
}
