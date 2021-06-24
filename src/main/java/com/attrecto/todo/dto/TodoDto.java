package com.attrecto.todo.dto;

import java.time.LocalDateTime;

import com.attrecto.todo.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TodoDto {
	private Long id;
	private String name;
	private String description;
	private LocalDateTime startTime;
	private Integer durationInMin;
	@JsonIgnore 
	private User user;
	
	public TodoDto(Long id, String name, String description, LocalDateTime startTime, Integer durationInMin,
			User user) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.durationInMin = durationInMin;
		this.user = user;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public Integer getDurationInMin() {
		return durationInMin;
	}
	public void setDurationInMin(Integer durationInMin) {
		this.durationInMin = durationInMin;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
