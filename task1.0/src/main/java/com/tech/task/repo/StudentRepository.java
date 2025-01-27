package com.tech.task.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.task.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	Optional<Student> findByName(String username);

	Optional<Student> findById(int id);

}
