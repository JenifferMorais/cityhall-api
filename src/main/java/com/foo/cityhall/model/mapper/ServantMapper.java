package com.foo.cityhall.model.mapper;

import org.springframework.stereotype.Component;

import com.foo.cityhall.model.dto.city​hall.SecretariatDTO;
import com.foo.cityhall.model.dto.city​hall.ServantDTO;
import com.foo.cityhall.model.entity.city​hall.Secretariat;
import com.foo.cityhall.model.entity.city​hall.Servant;

@Component
public class ServantMapper implements BaseMapper<Servant, ServantDTO> {

	private final SecretariatMapper secretariatMapper;

	public ServantMapper(SecretariatMapper secretariatMapper) {
		this.secretariatMapper = secretariatMapper;
	}

	@Override
	public ServantDTO toDto(Servant entity) {
		if (entity == null)
			return null;
		
		ServantDTO dto = new ServantDTO();
		
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setEmail(entity.getEmail());
		dto.setBirthday(entity.getBirthday());
		
		Secretariat sec = entity.getSecretariat();
		
		if (sec != null)
			dto.setSecretariat(secretariatMapper.toDto(sec));
		return dto;
	}

	@Override
	public Servant toEntity(ServantDTO dto) {
		if (dto == null)
			return null;
		
		Servant entity = new Servant();
		
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setBirthday(dto.getBirthday());
		
		SecretariatDTO secDto = dto.getSecretariat();
		
		if (secDto != null)
			entity.setSecretariat(secretariatMapper.toEntity(secDto));
		return entity;
	}

	@Override
	public void update(Servant entity, ServantDTO dto) {
		if (dto == null || entity == null)
			return;

		if (dto.getName() != null)
			entity.setName(dto.getName());
		
		if (dto.getEmail() != null)
			entity.setEmail(dto.getEmail());
		
		if (dto.getBirthday() != null)
			entity.setBirthday(dto.getBirthday());

		if (dto.getSecretariat() != null) {
			if (entity.getSecretariat() == null) {
				entity.setSecretariat(secretariatMapper.toEntity(dto.getSecretariat()));
			} else {
				secretariatMapper.update(entity.getSecretariat(), dto.getSecretariat());
			}
		}
	}
}
