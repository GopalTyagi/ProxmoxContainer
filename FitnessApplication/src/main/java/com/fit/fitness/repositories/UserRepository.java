package com.fit.fitness.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fit.fitness.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String username);

	User findByEmail(String email);

	List<User> findByRoleId(Long roleId);

}
