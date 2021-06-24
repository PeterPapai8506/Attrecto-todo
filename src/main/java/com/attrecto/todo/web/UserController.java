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

import com.attrecto.todo.dto.UserDto;
import com.attrecto.todo.mapper.UserMapper;
import com.attrecto.todo.model.User;
import com.attrecto.todo.service.TodoService;
import com.attrecto.todo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	TodoService todoService;
	
	@Autowired
	UserMapper userMapper;
	
	@GetMapping
	public List<UserDto> getAll(){
		return userMapper.usersToDto(userService.findAll());
	}
	
	@GetMapping("/{id}")
	public UserDto getById(@PathVariable long id) {
		User user = findByIdOrThrow(userService, id);
		return userMapper.userToDto(user);
	}
	
	@PostMapping
	public UserDto addUser(@RequestBody @Valid UserDto userDto) {
		return userMapper.userToDto(userService.save(userMapper.dtoToUser(userDto)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> modifyUser(@PathVariable long id, @RequestBody @Valid UserDto userDto) {
		userDto.setId(id);
		User updatedUser = userService.update(userMapper.dtoToUser(userDto));
		if(updatedUser == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(userMapper.userToDto(updatedUser));
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.delete(id);
	}
}
