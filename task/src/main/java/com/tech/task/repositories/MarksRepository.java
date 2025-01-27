package com.tech.task.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.tech.task.entities.Marks;

public interface MarksRepository extends JpaRepository<Marks, Integer> {

	@Query("SELECT m FROM Marks m " + "JOIN m.subject s " + "WHERE m.student.studentId = :studentId "
			+ "ORDER BY m.marks DESC")
	List<Marks> findMarksByStudent_studentId(@Param("studentId") int studentId);

}
