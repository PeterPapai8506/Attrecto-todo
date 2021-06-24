package com.attrecto.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attrecto.todo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{

}
