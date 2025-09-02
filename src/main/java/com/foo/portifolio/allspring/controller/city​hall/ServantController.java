package com.foo.portifolio.allspring.controller.city​hall;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foo.portifolio.allspring.controller.BaseController;
import com.foo.portifolio.allspring.model.dto.city​hall.ServantDTO;
import com.foo.portifolio.allspring.model.entity.city​hall.Servant;
import com.foo.portifolio.allspring.service.BaseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("servants")
public class ServantController extends BaseController<Servant, ServantDTO, Integer> {

	public ServantController(BaseService<Servant, ServantDTO, Integer> service) {
		super(service);
	}

	@PostMapping
	public ServantDTO create(@RequestBody @Valid ServantDTO dto) {
		return service.create(dto);
	}
}
