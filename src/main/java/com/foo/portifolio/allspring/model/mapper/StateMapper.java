package com.foo.portifolio.allspring.model.mapper;

import org.springframework.stereotype.Service;

import com.foo.portifolio.allspring.model.dto.todo.StateDTO;
import com.foo.portifolio.allspring.model.entity.todo.State;

@Service
public class StateMapper implements BaseMapper<State, StateDTO>{

	@Override
	public State toEntity(StateDTO dto) {
		State e = new State();
		e.setId(dto.getId());
		e.setName(dto.getName());
		return e;
	}

	@Override
	public StateDTO toDto(State entity) {
		StateDTO dto = new StateDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	@Override
	public void update(State entity, StateDTO dto) {
		entity.setId(dto.getId());
		entity.setName(dto.getName());
	}

}
