package com.attrecto.todo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.attrecto.todo.dto.TodoDto;
import com.attrecto.todo.model.Todo;

@Mapper(componentModel = "spring") 
public interface TodoMapper {
 	TodoDto todoToDto(Todo todo);
	Todo dtoToTodo(TodoDto todoDto);
	
	List<TodoDto> todosToDto(List<Todo> todos);
	List<Todo> dtosToTodos(List<TodoDto> todoDtos);
}
