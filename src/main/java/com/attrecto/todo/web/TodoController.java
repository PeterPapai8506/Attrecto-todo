package com.attrecto.todo.web;

import static com.attrecto.todo.util.TodoUtil.findByIdOrThrow;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attrecto.todo.dto.TodoDto;
import com.attrecto.todo.mapper.TodoMapper;
import com.attrecto.todo.model.Todo;
import com.attrecto.todo.model.User;
import com.attrecto.todo.service.TodoService;
import com.attrecto.todo.service.UserService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

	@Autowired
	private TodoMapper todoMapper;
	
	@Autowired
	private TodoService todoService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<TodoDto> getAll(){
		return todoMapper.todosToDto(todoService.findAll());
	}
	
	@GetMapping("/{id}")
	public TodoDto getById(@PathVariable long id) {
		Todo todo = findByIdOrThrow(todoService, id);
		return todoMapper.todoToDto(todo);
	}
	
	@PostMapping("/{id}")
	public TodoDto addTodo(@PathVariable long id, @RequestBody @Valid TodoDto todoDto) {
		User user = findByIdOrThrow(userService, id);
		Todo todo = todoMapper.dtoToTodo(todoDto);
		todo.setUser(user);
		user.addTodo(todo);
		
		return todoMapper.todoToDto(todoService.save(todo));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TodoDto> modifyTodo(@PathVariable long id, @RequestBody @Valid TodoDto todoDto) {
		todoDto.setId(id);
		Todo updatedTodo = todoService.update(todoMapper.dtoToTodo(todoDto));
		if(updatedTodo == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(todoMapper.todoToDto(updatedTodo));
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteTodo(@PathVariable long id) {
		Todo todo = findByIdOrThrow(todoService, id);
		todo.setUser(null);
		todoService.delete(id);
	}
}
