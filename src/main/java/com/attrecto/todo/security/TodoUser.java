package com.attrecto.todo.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.attrecto.todo.model.User;

public class TodoUser extends org.springframework.security.core.userdetails.User{
	private static final long serialVersionUID = 1L;
	private User user;

	public TodoUser(String username, String password, Collection<? extends GrantedAuthority> authorities, User user) {
		super(username, password, authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
