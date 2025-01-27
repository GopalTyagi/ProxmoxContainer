package com.tech.task.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tech.task.dto.StudentDTO;
import com.tech.task.entities.Marks;
import com.tech.task.entities.Student;
import com.tech.task.entities.Subject;
import com.tech.task.service.StudentService;
import com.tech.task.service.SubjectSevice;

@Controller
@RequestMapping("/home")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private SubjectSevice service;

	@PostMapping("/add")
	public ResponseEntity<Student> saveStudentDetails(@RequestBody Student student) {
		Student studentDetails = studentService.saveStudent(student);
		return new ResponseEntity<>(studentDetails, HttpStatus.CREATED);
	}

	@GetMapping
	public String getSingleStudentDetails(@RequestParam("studentId") int id, Model model) {
		Student student = studentService.getStudentById(id);
		List<Marks> subjectDetailsWithStudent = studentService.getStudentMarksById(id);

		if (student != null) {
			List<StudentDTO.SubjectMarks> subjectMarks = new ArrayList<>();

			for (Marks marks : subjectDetailsWithStudent) {
				String subjectName = marks.getSubject().getSubjectName();
				int marksValue = marks.getMarks();
				subjectMarks.add(new StudentDTO.SubjectMarks(subjectName, marksValue));
			}
			Collections.sort(subjectMarks, new Comparator<StudentDTO.SubjectMarks>() {

				public int compare(StudentDTO.SubjectMarks o1, StudentDTO.SubjectMarks o2) {
					return Integer.compare(o2.getMarks(), o1.getMarks()); // Descending order
				}
			});

			model.addAttribute("student", student);
			model.addAttribute("subjectMarks", subjectMarks);
			return "Student";
		} else {
			model.addAttribute("errorMessage", "Student not found");
			return "error";
		}
	}

	@GetMapping("/all")
	public String getAllStudentDetails(@RequestParam("subjectId") int subjectId, Model model) throws Exception {

		try {
			Subject studentDetails = service.getSubjectByIdSubject(subjectId);

			model.addAttribute("studentDetails", studentDetails);

			return "Subjectdetails";
		} catch (Exception e) {
			 model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}

	}

}