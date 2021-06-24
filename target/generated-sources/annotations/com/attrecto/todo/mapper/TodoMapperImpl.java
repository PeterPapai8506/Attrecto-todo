package com.attrecto.todo.mapper;

import com.attrecto.todo.dto.TodoDto;
import com.attrecto.todo.model.Todo;
import com.attrecto.todo.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-25T01:35:35+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.17.0.v20190306-2240, environment: Java 1.8.0_271 (Oracle Corporation)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Override
    public TodoDto todoToDto(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        LocalDateTime startTime = null;
        Integer durationInMin = null;
        User user = null;

        id = todo.getId();
        name = todo.getName();
        description = todo.getDescription();
        startTime = todo.getStartTime();
        durationInMin = todo.getDurationInMin();
        user = todo.getUser();

        TodoDto todoDto = new TodoDto( id, name, description, startTime, durationInMin, user );

        return todoDto;
    }

    @Override
    public Todo dtoToTodo(TodoDto todoDto) {
        if ( todoDto == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setId( todoDto.getId() );
        todo.setName( todoDto.getName() );
        todo.setDescription( todoDto.getDescription() );
        todo.setStartTime( todoDto.getStartTime() );
        todo.setDurationInMin( todoDto.getDurationInMin() );
        todo.setUser( todoDto.getUser() );

        return todo;
    }

    @Override
    public List<TodoDto> todosToDto(List<Todo> todos) {
        if ( todos == null ) {
            return null;
        }

        List<TodoDto> list = new ArrayList<TodoDto>( todos.size() );
        for ( Todo todo : todos ) {
            list.add( todoToDto( todo ) );
        }

        return list;
    }

    @Override
    public List<Todo> dtosToTodos(List<TodoDto> todoDtos) {
        if ( todoDtos == null ) {
            return null;
        }

        List<Todo> list = new ArrayList<Todo>( todoDtos.size() );
        for ( TodoDto todoDto : todoDtos ) {
            list.add( dtoToTodo( todoDto ) );
        }

        return list;
    }
}
