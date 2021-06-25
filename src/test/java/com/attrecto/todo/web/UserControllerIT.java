package com.attrecto.todo.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.attrecto.todo.dto.LoginDto;
import com.attrecto.todo.dto.UserDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class UserControllerIT {

	private static final String BASE_URI = "/api/users";

	@Autowired
	WebTestClient webTestClient;

	private String jwtToken;

	@BeforeEach
	public void init() {
		LoginDto body = new LoginDto();
		body.setUsername("admin");
		body.setPassword("pass");
		jwtToken = webTestClient.post().uri("/api/login").bodyValue(body).exchange().expectBody(String.class)
				.returnResult().getResponseBody();
	}

	@Test
	void testThatAddedUserCanGetById() throws Exception {
		List<UserDto> usersBefore = getAllUsers();

		UserDto newUser = new UserDto(null, "user10", new ArrayList<>(), null, "user10", "pass", "USER");
		UserDto savedUser = saveUser(newUser).expectStatus().isOk().expectBody(UserDto.class).returnResult()
				.getResponseBody();

		List<UserDto> usersAfter = getAllUsers();
		
		UserDto userById = getUserById(savedUser.getId()).expectStatus().isOk().expectBody(UserDto.class).returnResult()
				.getResponseBody();

		assertThat(usersAfter.size()).isEqualTo(usersBefore.size() + 1);
		
		assertThat(savedUser).usingRecursiveComparison().ignoringFields("id", "password")
		.isEqualTo(userById);
	}

	@Test
	void testThatNewValidUserCanBeSaved() throws Exception {
		List<UserDto> usersBefore = getAllUsers();

		UserDto newUser = new UserDto(null, "user", new ArrayList<>(), null, "user5", "pass", "USER");
		saveUser(newUser).expectStatus().isOk();

		List<UserDto> usersAfter = getAllUsers();

		assertThat(usersAfter.size()).isEqualTo(usersBefore.size() + 1);

		assertThat(usersAfter.get(usersAfter.size() - 1)).usingRecursiveComparison().ignoringFields("id", "password")
				.isEqualTo(newUser);
	}

	@Test
	void testThatNewInvalidUserCannotBeSaved() throws Exception {
		List<UserDto> usersBefore = getAllUsers();

		UserDto newUser = newInvalidUser();
		saveUser(newUser).expectStatus().isBadRequest();

		List<UserDto> usersAfter = getAllUsers();

		assertThat(usersAfter).hasSameSizeAs(usersBefore);
	}

	private UserDto newInvalidUser() {
		return new UserDto(null, null, null, null, "user", "pass", "USER2");
	}

	@Test
	void testThatUserCanBeUpdatedWithValidFields() throws Exception {
		UserDto newUser = new UserDto(null, "user6", new ArrayList<>(), null, "user6", "pass", "USER");
		UserDto savedUser = saveUser(newUser).expectStatus().isOk().expectBody(UserDto.class).returnResult()
				.getResponseBody();

		List<UserDto> usersBefore = getAllUsers();
		savedUser.setName("modified");
		modifyUser(savedUser).expectStatus().isOk();

		List<UserDto> usersAfter = getAllUsers();

		assertThat(usersAfter).hasSameSizeAs(usersBefore);
		assertThat(usersAfter.get(usersAfter.size() - 1)).usingRecursiveComparison().isEqualTo(savedUser);
	}

	@Test
	void testThatUserCannotBeUpdatedWithInvalidFields() throws Exception {
		UserDto newUser = new UserDto(null, "user8", new ArrayList<>(), null, "user8", "pass", "USER");
		UserDto savedUser = saveUser(newUser).expectStatus().isOk().expectBody(UserDto.class).returnResult()
				.getResponseBody();

		List<UserDto> usersBefore = getAllUsers();
		UserDto invalidUser = newInvalidUser();
		invalidUser.setId(savedUser.getId());
		modifyUser(invalidUser).expectStatus().isBadRequest();

		List<UserDto> usersAfter = getAllUsers();

		assertThat(usersAfter).hasSameSizeAs(usersBefore);
		assertThat(usersAfter.get(usersAfter.size() - 1)).usingRecursiveComparison().isEqualTo(savedUser);
	}

	@Test
	void testThatUserCanBeDeleted() throws Exception {
		UserDto newUser = new UserDto(null, "user9", new ArrayList<>(), null, "user9", "pass", "USER");
		UserDto savedUser = saveUser(newUser).expectStatus().isOk().expectBody(UserDto.class).returnResult()
				.getResponseBody();

		List<UserDto> usersBefore = getAllUsers();
		deleteUser(savedUser).expectStatus().isOk();

		List<UserDto> usersAfter = getAllUsers();

		assertThat(usersAfter).hasSize(usersBefore.size() - 1);
		assertThat(usersAfter).doesNotContain(savedUser);
	}

	private ResponseSpec getUserById(long id) {
		String path = BASE_URI + "/" + id;
		return webTestClient.get().uri(path).headers(headers -> headers.setBearerAuth(jwtToken)).exchange();
	}

	private ResponseSpec modifyUser(UserDto user) {
		String path = BASE_URI + "/" + user.getId();
		return webTestClient.put().uri(path).headers(headers -> headers.setBearerAuth(jwtToken)).bodyValue(user)
				.exchange();
	}

	private ResponseSpec deleteUser(UserDto user) {
		String path = BASE_URI + "/" + user.getId();
		return webTestClient.delete().uri(path).headers(headers -> headers.setBearerAuth(jwtToken)).exchange();
	}

	private ResponseSpec saveUser(UserDto newUser) {
		return webTestClient.post().uri(BASE_URI).bodyValue(newUser).headers(headers -> headers.setBearerAuth(jwtToken))
				.exchange();
	}

	private List<UserDto> getAllUsers() {
		List<UserDto> responseList = webTestClient.get().uri(BASE_URI)
				.headers(headers -> headers.setBearerAuth(jwtToken)).exchange().expectStatus().isOk()
				.expectBodyList(UserDto.class).returnResult().getResponseBody();
		Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		return responseList;
	}
}