package com.foo.portifolio.allspring.service.city​hall;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foo.portifolio.allspring.model.dto.city​hall.TodoDTO;
import com.foo.portifolio.allspring.model.entity.city​hall.State;
import com.foo.portifolio.allspring.model.entity.city​hall.Todo;
import com.foo.portifolio.allspring.model.mapper.BaseMapper;
import com.foo.portifolio.allspring.repository.BaseRepository;
import com.foo.portifolio.allspring.repository.city​hall.StateRepository;
import com.foo.portifolio.allspring.service.BaseService;

@Service
public class TodoService extends BaseService<Todo, TodoDTO, Integer> {

	private StateRepository stateRepository;

	public TodoService(BaseMapper<Todo, TodoDTO> mapper, BaseRepository<Todo, Integer> repository,
			StateRepository stateRepository) {
		super(mapper, repository);
		this.stateRepository = stateRepository;
	}

	@Override
	public TodoDTO update(TodoDTO dto) {
		Todo todo = super.repository.findById(dto.getId()).orElseThrow();
		super.mapper.update(todo, dto);
		if (Optional.ofNullable(todo.getState()).isPresent()
				&& !Optional.ofNullable(todo.getState()).map(t -> t.getId()).equals(dto.getStateId())) {
			State state = stateRepository.findById(dto.getStateId()).orElseThrow();
			todo.setState(state);
		}
		super.repository.save(todo);
		return super.mapper.toDto(todo);
	}

}
