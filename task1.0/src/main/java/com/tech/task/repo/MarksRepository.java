package com.tech.task.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tech.task.entity.SubjectDetailsWithStudent;

public interface MarksRepository extends JpaRepository<SubjectDetailsWithStudent, Integer> {

	 @Query("SELECT sm FROM SubjectDetailsWithStudent sm " +
	           "JOIN sm.subject s " +
	           "WHERE sm.student.studentId = :studentId " +
	           "ORDER BY sm.marks DESC")
	 	List<SubjectDetailsWithStudent> findMarksByStudentId(@Param("studentId") int studentId);
	 
	    List<SubjectDetailsWithStudent> findBySubject_SubjectId(int subjectId);


}
