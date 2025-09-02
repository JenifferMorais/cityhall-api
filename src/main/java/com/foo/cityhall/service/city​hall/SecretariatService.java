package com.foo.cityhall.service.city​hall;

import org.springframework.stereotype.Service;

import com.foo.cityhall.model.dto.city​hall.SecretariatDTO;
import com.foo.cityhall.model.entity.city​hall.Secretariat;
import com.foo.cityhall.model.mapper.BaseMapper;
import com.foo.cityhall.repository.BaseRepository;
import com.foo.cityhall.service.BaseService;


@Service
public class SecretariatService extends BaseService<Secretariat, SecretariatDTO,
Integer> {

	public SecretariatService(BaseMapper<Secretariat, SecretariatDTO> mapper, 
			BaseRepository<Secretariat, Integer> repository) {
		super(mapper, repository);
	}


}
