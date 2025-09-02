package com.foo.portifolio.allspring.service.user;

import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foo.portifolio.allspring.exception.BusinessException;
import com.foo.portifolio.allspring.model.dto.user.UserDTO;
import com.foo.portifolio.allspring.model.entity.user.User;
import com.foo.portifolio.allspring.model.mapper.BaseMapper;
import com.foo.portifolio.allspring.repository.auth.UserRepository;
import com.foo.portifolio.allspring.service.BaseService;

@Service
public class UserService extends BaseService<User, UserDTO, Integer> {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserService(BaseMapper<User, UserDTO> mapper, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		super(mapper, userRepository);
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDTO create(UserDTO dto) {
		if (userRepository.existsByUsername(dto.getUsername()))
			throw new BusinessException("username indisponível");
		if (dto.getPassword() == null || dto.getPassword().isBlank())
			throw new BusinessException("senha obrigatória");

		User e = mapper.toEntity(dto);
		e.setPassword(passwordEncoder.encode(dto.getPassword()));
		e = userRepository.save(e);
		return mapper.toDto(e);
	}

	@Override
	public UserDTO update(UserDTO dto) {
		var e = userRepository.findById(dto.getId()).orElseThrow(() -> new BusinessException("usuário não encontrado"));
		mapper.update(e, dto);
		if (dto.getPassword() != null && !dto.getPassword().isBlank())
			e.setPassword(passwordEncoder.encode(dto.getPassword()));
		e = userRepository.save(e);
		return mapper.toDto(e);
	}

	public User getByUsernameOrThrow(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new BusinessException("usuário não encontrado"));
	}
}
