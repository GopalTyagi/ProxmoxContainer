package com.tech.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.task.DTO.StudentMarksDTO;
import com.tech.task.DTO.StudentMarksDTO.SubjectMarks;
import com.tech.task.entity.Student;
import com.tech.task.entity.Subject;
import com.tech.task.entity.SubjectDetailsWithStudent;
import com.tech.task.repo.MarksRepository;
import com.tech.task.repo.StudentRepository;
import com.tech.task.repo.SubjectRepository;

@Service
public class MarksService {
	
	@Autowired
	private MarksRepository marksRepo;
	
	
	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	
	
	public SubjectDetailsWithStudent saveMarksForStudent(SubjectDetailsWithStudent subjectDetailsWithStudent) {
		return marksRepo.save(subjectDetailsWithStudent);
	}

	
	List<SubjectDetailsWithStudent>  getStudentDetailsWithMarksById(int studentId){
		return marksRepo.findMarksByStudentId(studentId);
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
	
	
	
	public List<StudentMarksDTO> getAllStudentDetailsWithSubjectsAndMarks() {
	    // Retrieve all mappings without filtering by subjectId
	    List<SubjectDetailsWithStudent> mappings = marksRepo.findAll(); // Assuming you have a method to get all mappings

	    // Group the mappings by student ID
	    Map<Integer, List<StudentMarksDTO.SubjectMarks>> subjectMarksByStudent = mappings.stream()
	            .collect(Collectors.groupingBy(
	                    m -> m.getStudent().getStudentId(),
	                    Collectors.mapping(
	                            m -> new StudentMarksDTO.SubjectMarks(m.getSubject().getSubjectName(), m.getMarks()),
	                            Collectors.toList()
	                    )
	            ));

	    // Construct the list of StudentMarksDTO
	    return subjectMarksByStudent.entrySet().stream()
	            .map(entry -> {
	                int studentId = entry.getKey();
	                List<StudentMarksDTO.SubjectMarks> subjectMarks = entry.getValue();

	                // Retrieve the student details from the first mapping (assuming all mappings for a student have the same details)
	                SubjectDetailsWithStudent firstMapping = mappings.stream()
	                        .filter(m -> m.getStudent().getStudentId() == studentId)
	                        .findFirst()
	                        .orElse(null);

	                return new StudentMarksDTO(
	                        studentId,
	                        firstMapping.getStudent().getName(),
	                        firstMapping.getStudent().getEmail(),
	                        subjectMarks
	                );
	            })
	            .collect(Collectors.toList());
	}
}
