package com.foo.cityhall.model.mapper;

import org.springframework.stereotype.Component;

import com.foo.cityhall.model.dto.city​hall.SecretariatDTO;
import com.foo.cityhall.model.entity.city​hall.Secretariat;

@Component
public class SecretariatMapper implements BaseMapper<Secretariat, SecretariatDTO> {

	@Override
	public SecretariatDTO toDto(Secretariat entity) {
		if (entity == null)
			return null;

		SecretariatDTO dto = new SecretariatDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setAcronym(entity.getAcronym());
		return dto;
	}

	@Override
	public Secretariat toEntity(SecretariatDTO dto) {
		if (dto == null)
			return null;

		Secretariat entity = new Secretariat();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setAcronym(dto.getAcronym());
		return entity;
	}

	@Override
	public void update(Secretariat entity, SecretariatDTO dto) {
		 if (dto == null || entity == null) return;
	        if (dto.getName() != null)    entity.setName(dto.getName());
	        if (dto.getAcronym() != null) entity.setAcronym(dto.getAcronym());
	}
}
