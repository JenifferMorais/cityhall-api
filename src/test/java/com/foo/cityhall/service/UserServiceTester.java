package com.foo.cityhall.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.quality.Strictness;
import com.foo.cityhall.exception.BusinessException;
import com.foo.cityhall.model.dto.user.UserDTO;
import com.foo.cityhall.model.entity.user.User;
import com.foo.cityhall.model.mapper.BaseMapper;
import com.foo.cityhall.repository.auth.UserRepository;
import com.foo.cityhall.service.user.UserService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTester {

	private UserService userService;

	@Mock
	private BaseMapper<User, UserDTO> mapper;

	@Mock
	private UserRepository repository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		when(mapper.toEntity(any(UserDTO.class))).thenAnswer(inv -> {
			UserDTO d = inv.getArgument(0);
			User e = new User();
			e.setId(d.getId());
			e.setUsername(d.getUsername());
			e.setPassword(d.getPassword());
			return e;
		});
		when(mapper.toDto(any(User.class))).thenAnswer(inv -> {
			User e = inv.getArgument(0);
			UserDTO d = new UserDTO();
			d.setId(e.getId());
			d.setUsername(e.getUsername());
			return d;
		});

		userService = new UserService(mapper, repository, passwordEncoder);
	}

	@Test
	void create_EncodesPassword_AndReturnsDto() {
		UserDTO dto = new UserDTO();
		dto.setUsername("john");
		dto.setPassword("raw123");

		when(repository.existsByUsername("john")).thenReturn(false);
		when(passwordEncoder.encode("raw123")).thenReturn("ENC(raw123)");
		when(repository.save(any(User.class))).thenAnswer(inv -> {
			User e = inv.getArgument(0);
			e.setId(1);
			return e;
		});

		UserDTO result = userService.create(dto);

		assertNotNull(result.getId());
		assertEquals("john", result.getUsername());

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(repository).save(captor.capture());
		assertEquals("ENC(raw123)", captor.getValue().getPassword());
	}

	@Test
	void create_Throws_WhenUsernameExists() {
		UserDTO dto = new UserDTO();
		dto.setUsername("john");
		dto.setPassword("x");

		when(repository.existsByUsername("john")).thenReturn(true);

		assertThrows(BusinessException.class, () -> userService.create(dto));
	}

	@Test
	void create_Throws_WhenPasswordBlankOrNull() {
		UserDTO blank = new UserDTO();
		blank.setUsername("john");
		blank.setPassword("  ");
		assertThrows(BusinessException.class, () -> userService.create(blank));

		UserDTO noPass = new UserDTO();
		noPass.setUsername("john");
		assertThrows(BusinessException.class, () -> userService.create(noPass));
	}

	@Test
	void update_EncodesPassword_WhenProvided() {
		doAnswer(inv -> {
			User e = inv.getArgument(0);
			UserDTO d = inv.getArgument(1);
			if (d.getUsername() != null)
				e.setUsername(d.getUsername());
			return null;
		}).when(mapper).update(any(User.class), any(UserDTO.class));

		User existing = new User();
		existing.setId(5);
		existing.setUsername("old");
		existing.setPassword("oldHash");

		when(repository.findById(5)).thenReturn(Optional.of(existing));
		when(passwordEncoder.encode("newpass")).thenReturn("ENC(newpass)");
		when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

		UserDTO dto = new UserDTO();
		dto.setId(5);
		dto.setUsername("new");
		dto.setPassword("newpass");

		UserDTO result = userService.update(dto);

		assertEquals(5, result.getId());
		assertEquals("new", result.getUsername());

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(repository).save(captor.capture());
		assertEquals("new", captor.getValue().getUsername());
		assertEquals("ENC(newpass)", captor.getValue().getPassword());
	}

	@Test
	void update_DoesNotChangePassword_WhenBlank() { 
		doAnswer(inv -> {
			User e = inv.getArgument(0);
			UserDTO d = inv.getArgument(1);
			if (d.getUsername() != null)
				e.setUsername(d.getUsername());
			return null;
		}).when(mapper).update(any(User.class), any(UserDTO.class));

		User existing = new User();
		existing.setId(7);
		existing.setUsername("old");
		existing.setPassword("keepHash");

		when(repository.findById(7)).thenReturn(Optional.of(existing));
		when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

		UserDTO dto = new UserDTO();
		dto.setId(7);
		dto.setUsername("new");
		dto.setPassword("  ");

		UserDTO result = userService.update(dto);

		assertEquals(7, result.getId());
		assertEquals("new", result.getUsername());
		verifyNoInteractions(passwordEncoder);

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(repository).save(captor.capture());
		assertEquals("keepHash", captor.getValue().getPassword());
	}

	@Test
	void delete_CallsRepository() {
		userService.delete(3);
		verify(repository).deleteById(3);
	}

	@Test
	void read_MapsEntityToDto() {
		int id = 2;
		User user = new User();
		user.setId(id);
		user.setUsername("someUser");
		user.setPassword("hash");

		when(repository.findById(id)).thenReturn(Optional.of(user));

		UserDTO result = userService.read(id);

		assertEquals(id, result.getId());
		assertEquals("someUser", result.getUsername());
	}

	@Test
	void index_ReturnsPagedDtos() {
		User user = new User();
		user.setId(1);
		user.setUsername("Foooo");

		when(repository.findAll(any(Pageable.class)))
				.thenReturn(new PageImpl<>(Arrays.asList(user), Pageable.ofSize(10), 1));

		Page<UserDTO> page = userService.index(Pageable.ofSize(10));

		assertEquals(1, page.getTotalPages());
		assertEquals("Foooo", page.getContent().get(0).getUsername());
	}

	@Test
	void getByUsernameOrThrow_ReturnsUser() {
		User u = new User();
		u.setId(10);
		u.setUsername("alice");

		when(repository.findByUsername("alice")).thenReturn(Optional.of(u));

		User result = userService.getByUsernameOrThrow("alice");

		assertEquals(10, result.getId());
		assertEquals("alice", result.getUsername());
	}

	@Test
	void getByUsernameOrThrow_Throws_WhenNotFound() {
		when(repository.findByUsername("ghost")).thenReturn(Optional.empty());
		assertThrows(BusinessException.class, () -> userService.getByUsernameOrThrow("ghost"));
	}
}
