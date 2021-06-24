package com.attrecto.todo.service;

import java.util.Optional;

public interface Service<T>{
	Optional<T> findById(long id);
}
