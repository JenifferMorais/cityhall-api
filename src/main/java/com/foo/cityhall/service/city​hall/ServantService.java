package com.foo.cityhall.service.city​hall;

import org.springframework.stereotype.Service;

import com.foo.cityhall.model.dto.city​hall.ServantDTO;
import com.foo.cityhall.model.entity.city​hall.Servant;
import com.foo.cityhall.model.mapper.BaseMapper;
import com.foo.cityhall.repository.BaseRepository;
import com.foo.cityhall.service.BaseService;

@Service
public class ServantService extends BaseService<Servant, ServantDTO, Integer> {

	public ServantService(BaseMapper<Servant, ServantDTO> mapper, BaseRepository<Servant, Integer> repository) {
		super(mapper, repository);
	}


}
