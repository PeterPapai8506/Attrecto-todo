package com.attrecto.todo.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Todo.class)
public abstract class Todo_ {

	public static volatile SingularAttribute<Todo, Integer> durationInMin;
	public static volatile SingularAttribute<Todo, String> name;
	public static volatile SingularAttribute<Todo, String> description;
	public static volatile SingularAttribute<Todo, LocalDateTime> startTime;
	public static volatile SingularAttribute<Todo, Long> id;
	public static volatile SingularAttribute<Todo, User> user;

	public static final String DURATION_IN_MIN = "durationInMin";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String USER = "user";

}

