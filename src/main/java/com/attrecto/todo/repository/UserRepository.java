package com.attrecto.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attrecto.todo.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
