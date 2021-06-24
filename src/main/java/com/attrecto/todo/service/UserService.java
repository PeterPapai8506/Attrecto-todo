package com.attrecto.todo.service;

import static com.attrecto.todo.util.TodoUtil.checkUserRoleMatch;
import static com.attrecto.todo.util.TodoUtil.getUserNameIfRoleMatch;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.attrecto.todo.model.Role;
import com.attrecto.todo.model.Todo;
import com.attrecto.todo.model.User;
import com.attrecto.todo.repository.TodoRepository;
import com.attrecto.todo.repository.UserRepository;

import javassist.NotFoundException;

@org.springframework.stereotype.Service
public class UserService implements Service<User>{
	@Autowired
	UserRepository userRepository;

	@Autowired
	TodoRepository todoRepository;
	
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		Optional<String> optUserName = getUserNameIfRoleMatch(Role.USER);
		
		if(optUserName.isPresent()) {
			User filteredUser = users.stream().filter(u -> u.getUsername().equals(optUserName.get())).findFirst().get();
			users.clear();
			users.add(filteredUser);
		}
		
		return users;
	}
	
	public Optional<User> findById(long id){
		Optional<String> optUserName = getUserNameIfRoleMatch(Role.USER);
		Optional<User> optUser = userRepository.findById(id);
		if(optUserName.isPresent()) {
			User user = optUser.get();
			String userName = optUserName.get();
			
			if(user.getUsername().equals(userName)) {
				return optUser;
			}
			
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		} 
		
		return optUser;
	}
	
	@Transactional
	public User save(User user) {
		try {
			return userRepository.save(user);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
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
		checkUserRoleMatch(userRepository, user);
		Todo savedTodo = todoRepository.save(todo);
		user.addTodo(savedTodo);
		todo.setUser(user);
		return user;
	}
	
	@Transactional
	public User deleteTodo(long id, long todoId) {
		User user = userRepository.findById(id).get();
		checkUserRoleMatch(userRepository, user);
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
		checkUserRoleMatch(userRepository, user);
		Todo oldTodo = optTodo.get();
		oldTodo.setUser(null);
		user.getTodos().remove(oldTodo);
		user.getTodos().add(todo);
		todoRepository.save(oldTodo);
		todoRepository.save(todo);
		return user;
	}
	
	@Transactional
	public void saveImageFile(Long id, MultipartFile file) throws IOException {
	    User user = userRepository.findById(id).get();
		checkUserRoleMatch(userRepository, user);
		
	    user.setImage(file.getBytes());
	    userRepository.save(user);
	}
}
