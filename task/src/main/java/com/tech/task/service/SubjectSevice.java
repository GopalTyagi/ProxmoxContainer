package com.tech.task.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.task.entities.Subject;
import com.tech.task.exception.ResourceNotFoundException;
import com.tech.task.repositories.SubjectRepository;

@Service
public class SubjectSevice {

	@Autowired
	private SubjectRepository subjectRepo;
	
	public Subject saveSubjectDetails(Subject subject) {
		return subjectRepo.save(subject);
	}

	public Subject getSubjectByIdSubject(int subjectId) throws Exception {
		Subject subjects = subjectRepo.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("404", "Student not found with id: " + subjectId));

		subjects.setList(subjectRepo.getSubjectId(subjectId));

		return subjects;
	}

}