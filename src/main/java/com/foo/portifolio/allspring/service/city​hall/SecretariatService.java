package com.foo.portifolio.allspring.service.city​hall;

import org.springframework.stereotype.Service;

import com.foo.portifolio.allspring.model.dto.city​hall.SecretariatDTO;
import com.foo.portifolio.allspring.model.entity.city​hall.Secretariat;
import com.foo.portifolio.allspring.model.mapper.BaseMapper;
import com.foo.portifolio.allspring.repository.BaseRepository;
import com.foo.portifolio.allspring.service.BaseService;


@Service
public class SecretariatService extends BaseService<Secretariat, SecretariatDTO,
Integer> {

	public SecretariatService(BaseMapper<Secretariat, SecretariatDTO> mapper, 
			BaseRepository<Secretariat, Integer> repository) {
		super(mapper, repository);
	}


}
