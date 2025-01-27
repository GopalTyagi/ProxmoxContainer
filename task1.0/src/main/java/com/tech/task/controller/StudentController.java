package com.tech.task.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tech.task.DTO.StudentMarksDTO;
import com.tech.task.entity.Student;
import com.tech.task.entity.SubjectDetailsWithStudent;
import com.tech.task.service.MarksService;
import com.tech.task.service.StudentService;

@Controller
@RequestMapping("/home")
public class StudentController {

	@Autowired
	private MarksService marksService;

	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public ResponseEntity<StudentMarksDTO> getStudentDetails(@RequestParam("studentId") int id) {
		Student student = studentService.getStudentById(id);
		List<SubjectDetailsWithStudent> subjectDetailsWithStudent = studentService.getStudentDetailsWithMarksById(id);

		if (student != null) {
			List<StudentMarksDTO.SubjectMarks> subjectMarks = subjectDetailsWithStudent.stream().map(
					marks -> new StudentMarksDTO.SubjectMarks(marks.getSubject().getSubjectName(), marks.getMarks()))
					.collect(Collectors.toList());

			return ResponseEntity.ok(
					new StudentMarksDTO(student.getStudentId(), student.getName(), student.getEmail(), subjectMarks));
		} else {
			return ResponseEntity.status(404).body(null); // Return 404 Not Found
		}
	}

//	@GetMapping("/allStudentsData")
//	public ResponseEntity<List<StudentMarksDTO>> getAllStudentDetails(
//			@RequestParam("subjectId") int subjectId,Model model) {
//		List<StudentMarksDTO> studentDetails = marksService.getAllStudentDetailsWithSubjectsAndMarks();
//		return ResponseEntity.ok(studentDetails);
//	}
	

	@GetMapping("/allStudentsData")
	public String getAllStudentDetails(
			@RequestParam("subjectId") int subjectId,Model model) {
		List<StudentMarksDTO> studentDetails = marksService.getAllStudentDetailsWithSubjectsAndMarks();
		model.addAttribute("studentDetails",studentDetails);
		
		return "allStudentsData";
	}

}
