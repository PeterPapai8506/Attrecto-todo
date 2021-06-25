package com.attrecto.todo.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.attrecto.todo.model.Todo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class UserDto {
	private Long id;
	@NotEmpty
	private String name;
	@JsonIgnore
	private List<Todo> todos;
	private byte[] image;
	@NotEmpty
	private String username;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@NotEmpty
	private String role;

	public UserDto() {
	}
	
	public UserDto(Long id, @NotEmpty String name, List<Todo> todos, byte[] image, String username, String password,
			String role) {
		super();
		this.id = id;
		this.name = name;
		this.todos = todos;
		this.image = image;
		this.username = username;
		this.password = password;
		this.role = role;
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
	public List<Todo> getTodos() {
		return todos;
	}
	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
