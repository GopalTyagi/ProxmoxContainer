package com.tech.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.task.entities.Marks;
import com.tech.task.repositories.MarksRepository;

@Service
public class MarksService {

	@Autowired
	private MarksRepository marksRepo;

	public Marks addMarksForStudent(Marks marks) {
		return marksRepo.save(marks);
	}

}
