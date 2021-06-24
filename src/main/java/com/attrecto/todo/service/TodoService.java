package com.attrecto.todo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.attrecto.todo.model.Todo;
import com.attrecto.todo.repository.TodoRepository;

@org.springframework.stereotype.Service
public class TodoService implements Service<Todo>{

	@Autowired
	TodoRepository todoRepository;
	
	public List<Todo> findAll() {
		return todoRepository.findAll();
	}
	
	public Optional<Todo> findById(long id){
		return todoRepository.findById(id);
	}
	
	@Transactional
	public Todo save(Todo todo) {
		return todoRepository.save(todo);
	}
	
	@Transactional
	public Todo update(Todo todo) {
		if(!todoRepository.existsById(todo.getId()))
			return null;
		return todoRepository.save(todo);
	}
	
	@Transactional
	public void delete(long id) {
		todoRepository.deleteById(id);
	}
		
}
