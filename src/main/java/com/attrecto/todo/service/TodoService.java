package com.attrecto.todo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.attrecto.todo.model.Todo;
import com.attrecto.todo.repository.TodoRepository;
import com.attrecto.todo.util.TodoUtil;
import com.attrecto.todo.util.TodoUtil.TodoAction;

@org.springframework.stereotype.Service
public class TodoService implements Service<Todo>{
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
	
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
		TodoUtil.logTodo(logger, todo, TodoAction.CREATE);
		return todoRepository.save(todo);
	}
	
	@Transactional
	public Todo update(Todo todo) {
		if(!todoRepository.existsById(todo.getId()))
			return null;
		TodoUtil.logTodo(logger, todo, TodoAction.UPDATE, todoRepository);
		return todoRepository.save(todo);
	}
	
	@Transactional
	public void delete(long id) {
		Optional<Todo> todo = todoRepository.findById(id);
		if(todo.isEmpty()) {
			return;
		}
		TodoUtil.logTodo(logger, todo.get(), TodoAction.DELETE);
		todoRepository.deleteById(id);
	}
}