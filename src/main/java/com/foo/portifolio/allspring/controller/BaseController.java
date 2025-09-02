package com.foo.portifolio.allspring.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.foo.portifolio.allspring.model.dto.BaseEntityDTO;
import com.foo.portifolio.allspring.model.entity.BaseEntity;
import com.foo.portifolio.allspring.service.BaseService;

@Service
public abstract class BaseController<Entity extends BaseEntity<ID>, DTO extends BaseEntityDTO<ID>, ID> {
	protected BaseService<Entity, DTO, ID> service;

	public BaseController(BaseService<Entity, DTO, ID> service) {
		super();
		this.service = service;
	}

	@GetMapping
	public Page<DTO> index(Pageable pageable) {
		return service.index(pageable);
	}

	@GetMapping("{id}")
	public DTO read(@PathVariable ID id) {
		return this.service.read(id);
	}

	@PutMapping
	public DTO update(@RequestBody DTO dto) {
		return service.update(dto);
	}

	@PostMapping
	public DTO create(@RequestBody DTO dto) {
		return service.create(dto);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable ID id) {
		service.delete(id);
	}
}
