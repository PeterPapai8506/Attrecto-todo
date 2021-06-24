package com.attrecto.todo.mapper;

import com.attrecto.todo.dto.UserDto;
import com.attrecto.todo.model.Todo;
import com.attrecto.todo.model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-25T01:35:35+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.17.0.v20190306-2240, environment: Java 1.8.0_271 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setName( user.getName() );
        List<Todo> list = user.getTodos();
        if ( list != null ) {
            userDto.setTodos( new ArrayList<Todo>( list ) );
        }
        byte[] image = user.getImage();
        if ( image != null ) {
            userDto.setImage( Arrays.copyOf( image, image.length ) );
        }
        userDto.setUsername( user.getUsername() );
        userDto.setPassword( user.getPassword() );
        userDto.setRole( user.getRole() );

        return userDto;
    }

    @Override
    public User dtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setName( userDto.getName() );
        user.setUsername( userDto.getUsername() );
        user.setPassword( userDto.getPassword() );
        user.setRole( userDto.getRole() );
        byte[] image = userDto.getImage();
        if ( image != null ) {
            user.setImage( Arrays.copyOf( image, image.length ) );
        }
        List<Todo> list = userDto.getTodos();
        if ( list != null ) {
            user.setTodos( new ArrayList<Todo>( list ) );
        }

        return user;
    }

    @Override
    public List<UserDto> usersToDto(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( userToDto( user ) );
        }

        return list;
    }

    @Override
    public List<User> dtosToUsers(List<UserDto> userDtos) {
        if ( userDtos == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDtos.size() );
        for ( UserDto userDto : userDtos ) {
            list.add( dtoToUser( userDto ) );
        }

        return list;
    }
}
