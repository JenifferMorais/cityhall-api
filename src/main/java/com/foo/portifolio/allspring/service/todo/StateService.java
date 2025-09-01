package com.foo.portifolio.allspring.service.todo;

import org.springframework.stereotype.Service;

import com.foo.portifolio.allspring.model.dto.todo.StateDTO;
import com.foo.portifolio.allspring.model.entity.todo.State;
import com.foo.portifolio.allspring.model.mapper.BaseMapper;
import com.foo.portifolio.allspring.repository.BaseRepository;
import com.foo.portifolio.allspring.service.BaseService;

@Service
public class StateService extends BaseService<State, StateDTO, Integer> {

	public StateService(BaseMapper<State, StateDTO> mapper, BaseRepository<State, Integer> repository) {
		super(mapper, repository);
	}

}
