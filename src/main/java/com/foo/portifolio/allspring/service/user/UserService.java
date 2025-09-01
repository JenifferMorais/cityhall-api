package com.foo.portifolio.allspring.service.user;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.foo.portifolio.allspring.exception.BusinessException;
import com.foo.portifolio.allspring.model.dto.user.UserDTO;
import com.foo.portifolio.allspring.model.entity.user.User;
import com.foo.portifolio.allspring.model.mapper.BaseMapper;
import com.foo.portifolio.allspring.model.mapper.UserMapper;
import com.foo.portifolio.allspring.repository.BaseRepository;
import com.foo.portifolio.allspring.repository.auth.UserRepository;
import com.foo.portifolio.allspring.service.BaseService;

@Service
public class UserService extends BaseService<User, UserDTO, Integer> {

	public UserService(BaseMapper<User, UserDTO> mapper,UserRepository repository) {
		super(mapper, repository);
	}

	@Override
	public UserDTO create(UserDTO dto){
		User findUser = new User();
		findUser.setUsername(dto.getUsername());
		if(this.repository.exists(Example.of(findUser)))
				throw new BusinessException("username indisponível");
		User e = this.repository.save(this.mapper.toEntity(dto));
		return this.mapper.toDto(e);
	}
}
