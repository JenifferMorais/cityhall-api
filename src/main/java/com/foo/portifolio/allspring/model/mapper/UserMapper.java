package com.foo.portifolio.allspring.model.mapper;

import org.springframework.stereotype.Service;

import com.foo.portifolio.allspring.model.dto.user.UserDTO;
import com.foo.portifolio.allspring.model.entity.user.User;

@Service
public class UserMapper implements BaseMapper<User, UserDTO> {

	@Override
	public User toEntity(UserDTO dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
		return user;
	}

	@Override
	public UserDTO toDto(User entity) {
		UserDTO  dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setUsername(entity.getUsername());
		return dto;
	}

	@Override
	public void update(User entity, UserDTO dto) {
		entity.setId(dto.getId());
		entity.setUsername(dto.getUsername());
		entity.setPassword(dto.getPassword());
	}

}
