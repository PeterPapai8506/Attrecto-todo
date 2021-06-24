package com.attrecto.todo.web;

import static com.attrecto.todo.util.TodoUtil.checkAdminRole;
import static com.attrecto.todo.util.TodoUtil.findByIdOrThrow;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.attrecto.todo.dto.TodoDto;
import com.attrecto.todo.dto.UserDto;
import com.attrecto.todo.mapper.TodoMapper;
import com.attrecto.todo.mapper.UserMapper;
import com.attrecto.todo.model.User;
import com.attrecto.todo.service.TodoService;
import com.attrecto.todo.service.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	TodoService todoService;
	
    @Autowired
	TodoMapper todoMapper;
    
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
		checkAdminRole();
		return userMapper.userToDto(userService.save(userMapper.dtoToUser(userDto)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> modifyUser(@PathVariable long id, @RequestBody @Valid UserDto userDto) {
		checkAdminRole();
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
		checkAdminRole();
		userService.delete(id);
	}
	
	@PostMapping("/{id}/todo")
	public UserDto addTodo(@PathVariable long id, @RequestBody @Valid TodoDto todoDto) {
		return userMapper.userToDto(userService.addTodo(id, todoMapper.dtoToTodo(todoDto)));
	}
	
	@PutMapping("/{id}/todo")
	public UserDto modifyTodo(@PathVariable long id, @RequestBody @Valid TodoDto todoDto) throws NotFoundException {
		return userMapper.userToDto(userService.updateTodo(id, todoMapper.dtoToTodo(todoDto)));
	}
	
	@DeleteMapping("/{id}/todo/{todoId}")
	public void deleteTodo(@PathVariable long id, @PathVariable long todoId) {
		userService.deleteTodo(id, todoId);
	}
	
    @PostMapping("/{id}/image")
	public String handleImagePost(@PathVariable long id, @RequestParam("imagefile") MultipartFile file) throws IOException{
	    userService.saveImageFile(id, file);
	
	    return "redirect:/api/users/images/" + id ;
	}
    
    @GetMapping(path = "/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable long id) throws Exception {
		User user = findByIdOrThrow(userService, id);

        byte[] image = user.getImage();
		ByteArrayResource resource = new ByteArrayResource(image);

        return ResponseEntity.ok().contentLength(image.length).contentType(MediaType.IMAGE_JPEG).body(resource);
    }
}
