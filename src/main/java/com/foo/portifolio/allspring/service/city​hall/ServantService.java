package com.foo.portifolio.allspring.service.city​hall;

import org.springframework.stereotype.Service;

import com.foo.portifolio.allspring.model.dto.city​hall.ServantDTO;
import com.foo.portifolio.allspring.model.entity.city​hall.Servant;
import com.foo.portifolio.allspring.model.mapper.BaseMapper;
import com.foo.portifolio.allspring.repository.BaseRepository;
import com.foo.portifolio.allspring.service.BaseService;

@Service
public class ServantService extends BaseService<Servant, ServantDTO, Integer> {

	public ServantService(BaseMapper<Servant, ServantDTO> mapper, BaseRepository<Servant, Integer> repository) {
		super(mapper, repository);
	}


}
