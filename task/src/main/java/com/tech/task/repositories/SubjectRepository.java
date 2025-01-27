package com.tech.task.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tech.task.dto.SubjectDTO;
import com.tech.task.entities.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

	@Query("SELECT \n" + "    s.studentId AS studentId, \n" + "    s.name AS name,\n" + "    s.email AS email,\n"
			+ "    m.marks AS marks\n" + "FROM Student s\n" + "JOIN Marks m ON m.student.studentId = s.studentId\n"
			+ "JOIN Subject su ON su.subjectId = m.subject.subjectId\n" + "WHERE su.subjectId = :subjectId\n"
			+ "ORDER BY m.marks DESC")
	List<SubjectDTO> getSubjectId(@Param("subjectId") int subjectId) throws Exception;

	Optional<Subject> findBySubjectName(String subjectName);
}
