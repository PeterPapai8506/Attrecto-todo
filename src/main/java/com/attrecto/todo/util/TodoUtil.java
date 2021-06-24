package com.attrecto.todo.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.attrecto.todo.service.Service;

public class TodoUtil {

	public static <T> T findByIdOrThrow(Service<T> service, long id) {
		return service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
