package com.attrecto.todo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.attrecto.todo.model.User;
import com.attrecto.todo.repository.UserRepository;
import com.attrecto.todo.security.TodoUser;

@Service
public class TodoUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		
		return new TodoUser(username, user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())), user);
	}
}
