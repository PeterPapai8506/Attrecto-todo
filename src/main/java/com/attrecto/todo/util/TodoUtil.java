package com.attrecto.todo.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import com.attrecto.todo.model.Role;
import com.attrecto.todo.model.User;
import com.attrecto.todo.repository.UserRepository;
import com.attrecto.todo.service.Service;

public class TodoUtil {

	public static <T> T findByIdOrThrow(Service<T> service, long id) {
		return service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public static void checkUserRoleMatch(UserRepository userRepository, User user) {
		Optional<String> optUserName = getUserNameIfRoleMatch(Role.USER);

		if (optUserName.isEmpty()) {
			return;
		}

		String username = optUserName.get();
		Optional<User> optUser = userRepository.findByUsername(username);
		if (optUser.isEmpty() || !optUser.get().getUsername().equals(user.getUsername())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	public static void checkAdminRole() {
		if (getUserNameIfRoleMatch(Role.ADMIN).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	public static Optional<String> getUserNameIfRoleMatch(String userRole) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;

		String userName = userDetails.getUsername();
		String role = userDetails.getAuthorities().stream().findFirst().get().toString();

		return userRole.equals(role) ? Optional.of(userName) : Optional.empty();
	}
}
