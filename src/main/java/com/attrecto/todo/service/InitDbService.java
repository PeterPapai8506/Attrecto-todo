package com.attrecto.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attrecto.todo.model.Role;
import com.attrecto.todo.model.User;

@Service
public class InitDbService {

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Transactional
	public void initDb() {
		userService.save(new User(null, "user1", "user", passwordEncoder.encode("pass"), Role.USER, null, null));
		userService.save(new User(null, "user2", "user2", passwordEncoder.encode("pass"), Role.USER, null, null));
		userService.save(new User(null, "user3", "admin", passwordEncoder.encode("pass"), Role.ADMIN, null, null));
	}
}
