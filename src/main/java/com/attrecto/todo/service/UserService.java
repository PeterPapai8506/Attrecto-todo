package com.attrecto.todo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.attrecto.todo.model.Todo;
import com.attrecto.todo.model.User;
import com.attrecto.todo.repository.TodoRepository;
import com.attrecto.todo.repository.UserRepository;
import com.attrecto.todo.service.Service;

import javassist.NotFoundException;

@org.springframework.stereotype.Service
public class UserService implements Service<User>{

	@Autowired
	UserRepository userRepository;

	@Autowired
	TodoRepository todoRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> findById(long id){
		return userRepository.findById(id);
	}
	
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public User update(User user) {
		if(!userRepository.existsById(user.getId()))
			return null;
		return userRepository.save(user);
	}
	
	@Transactional
	public void delete(long id) {
		userRepository.deleteById(id);
	}
	
	@Transactional
	public User addTodo(long id, Todo todo) {
		User user = userRepository.findById(id).get();
		Todo savedTodo = todoRepository.save(todo);
		user.addTodo(savedTodo);
		todo.setUser(user);
		return user;
	}
	
	@Transactional
	public User deleteTodo(long id, long todoId) {
		User user = userRepository.findById(id).get();
		Todo todo = todoRepository.findById(todoId).get();
		todo.setUser(null);
		user.getTodos().remove(todo);
		todoRepository.save(todo);
		return user;
	}
	
	@Transactional
	public User updateTodo(long id, Todo todo) throws NotFoundException {
		Optional<Todo> optTodo = todoRepository.findById(todo.getId());
		if(optTodo.isEmpty()){
			throw new NotFoundException("");
		}
		
		User user = userRepository.findById(id).get();
		Todo oldTodo = optTodo.get();
		oldTodo.setUser(null);
		user.getTodos().remove(oldTodo);
		user.getTodos().add(todo);
		todoRepository.save(oldTodo);
		todoRepository.save(todo);
		return user;
	}
}
