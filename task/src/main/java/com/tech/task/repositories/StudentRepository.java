package com.tech.task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.task.entities.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	
	Student findByName(String username);

	 Student findByEmailAndPassword(String email, String password);

	//Student findByUniqueKey11(String string, String string2);

	
}