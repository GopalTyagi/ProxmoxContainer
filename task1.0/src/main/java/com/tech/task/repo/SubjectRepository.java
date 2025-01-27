package com.tech.task.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.task.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Integer>{
	
    void deleteBySubjectId(int subjectId);
    List<Subject> findAll();
    Optional<Subject> findBySubjectName(String subjectName); // Method to find by subject name




}
