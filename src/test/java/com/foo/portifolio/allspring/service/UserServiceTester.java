package com.foo.portifolio.allspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.foo.cityhall.model.dto.user.UserDTO;
import com.foo.cityhall.model.entity.user.User;
import com.foo.cityhall.model.mapper.UserMapper;
import com.foo.cityhall.repository.auth.UserRepository;
import com.foo.cityhall.service.user.UserService;

@SpringBootTest
public class UserServiceTester {
	private UserService userService;
	@Mock
	private UserMapper mapper;
	@Mock
	private UserRepository repository;

	@BeforeEach
	void setUp() {
		when(mapper.toDto(any(User.class))).thenCallRealMethod();
		when(mapper.toEntity(any(UserDTO.class))).thenCallRealMethod();
		userService = new UserService(mapper, repository);
	}
	@Test
	void create() {
		UserDTO userDto = new UserDTO();
		User createdUser = new User();
		createdUser.setId(1);
		createdUser.setPassword("somePassword");
		createdUser.setUsername("Username");
		when(repository.exists(any(Example.class)))
		.thenReturn(false);
		when(repository.save(any(User.class)))
		.thenReturn(createdUser);
		UserDTO result = userService.create(userDto);
		assertNotNull(result.getId());
		assertNotEquals(result.getPassword(), createdUser.getPassword());
		assertEquals(createdUser.getUsername(), result.getUsername());
	}
	@Test
	void delete() {
		int id = 3;
		userService.delete(id);
		verify(repository).deleteById(id);
	}
	@Test
	void read() {
		int id = 2;
		User user = new User();
		user.setId(id);
		user.setPassword("somePassword");
		user.setUsername("someUserName");
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(user));
		UserDTO result = userService.read(id);
		assertEquals(user.getId(),result.getId());
		assertEquals(user.getUsername(),result.getUsername());
	}

	@Test
	void index() {
		User user = new User();
		user.setId(1);
		user.setUsername("Foooo");
		when(repository.findAll(any(Pageable.class)))
				.thenReturn(new PageImpl<User>(Arrays.asList(user), Pageable.ofSize(10), 1));
		Page<UserDTO> page = userService.index(Pageable.ofSize(10));
		assertEquals(page.getTotalPages(), 1);
	}
}
