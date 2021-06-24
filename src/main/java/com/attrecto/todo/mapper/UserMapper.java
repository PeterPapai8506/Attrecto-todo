package com.attrecto.todo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.attrecto.todo.dto.UserDto;
import com.attrecto.todo.model.User;

@Mapper(componentModel = "spring") 
public interface UserMapper {

	UserDto userToDto(User user);
	User dtoToUser(UserDto userDto);
	
	List<UserDto> usersToDto(List<User> users);
	List<User> dtosToUsers(List<UserDto> userDtos);
}
