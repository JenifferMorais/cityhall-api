package com.foo.portifolio.allspring.model.mapper;

public interface BaseMapper<Entity, DTO> {
	
	public Entity toEntity(DTO dto);
	
	public DTO toDto(Entity entity);
	
	public void update(Entity entity, DTO dto);
}
