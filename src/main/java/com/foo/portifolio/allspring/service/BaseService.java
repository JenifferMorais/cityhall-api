package com.foo.portifolio.allspring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foo.portifolio.allspring.model.dto.BaseEntityDTO;
import com.foo.portifolio.allspring.model.entity.BaseEntity;
import com.foo.portifolio.allspring.model.mapper.BaseMapper;
import com.foo.portifolio.allspring.repository.BaseRepository;

import jakarta.transaction.Transactional;

@Transactional
public abstract class BaseService<Entity extends BaseEntity<ID>, DTO extends BaseEntityDTO<ID>, ID> {
	
	public BaseService(BaseMapper<Entity, DTO> mapper, BaseRepository<Entity, ID> repository) {
		super();
		this.mapper = mapper;
		this.repository = repository;
	}
	
	protected BaseMapper<Entity,DTO> mapper;

	protected BaseRepository<Entity, ID> repository;
	
	public Page<DTO> index(Pageable pageable){
		return this.repository.findAll(pageable)
				.map(this.mapper::toDto);
	}
	
	public DTO create(DTO dto){
		Entity e = this.repository.save(this.mapper.toEntity(dto));
		return this.mapper.toDto(e);
	}
	public DTO read(ID id) {
		return this.repository
				.findById(id)
				.map(this.mapper::toDto)
				.orElseThrow();
	}
	public void delete(ID id) {
		this.repository.deleteById(id);
	}

	public DTO update(DTO dto) {
		 Entity entity = this.repository
				 .findById(dto.getId())
				 .orElseThrow();
		 mapper.update(entity, dto);
		 return mapper.toDto(repository.save(entity));
	}
}
