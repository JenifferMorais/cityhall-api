package com.foo.portifolio.allspring.model.mapper;

import org.springframework.stereotype.Service;

import com.foo.portifolio.allspring.model.dto.todo.TodoDTO;
import com.foo.portifolio.allspring.model.entity.todo.State;
import com.foo.portifolio.allspring.model.entity.todo.Todo;

@Service
public class TodoMapper implements BaseMapper<Todo, TodoDTO>{

	@Override
	public Todo toEntity(TodoDTO dto) {
		Todo e = new Todo();
		e.setId(dto.getId());
		e.setTitle(dto.getTitle());
		e.setContent(dto.getContent());
		e.setCreationData(dto.getCreationData());
		e.setState(new State(dto.getStateId()));
		return e;
	}

	@Override
	public TodoDTO toDto(Todo entity) {
		TodoDTO dto = new TodoDTO();
		dto.setId(entity.getId());
		dto.setContent(entity.getContent());
		dto.setTitle(entity.getTitle());
		dto.setStateId(entity.getState().getId());
		return dto;
	}

	@Override
	public void update(Todo entity, TodoDTO dto) {
		entity.setContent(dto.getContent());
		entity.setTitle(dto.getContent());
	}

}
