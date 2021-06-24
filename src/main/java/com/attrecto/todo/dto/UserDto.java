package com.attrecto.todo.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.attrecto.todo.model.Todo;

public class UserDto {
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	private List<Todo> todos;
	
	public UserDto(Long id, @NotEmpty String name, @NotEmpty String username, @NotEmpty String password,
			List<Todo> todos) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.todos = todos;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Todo> getTodos() {
		return todos;
	}
	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}
}
