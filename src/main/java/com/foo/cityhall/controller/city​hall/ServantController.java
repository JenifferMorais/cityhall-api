package com.foo.cityhall.controller.city​hall;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foo.cityhall.controller.BaseController;
import com.foo.cityhall.model.dto.city​hall.ServantDTO;
import com.foo.cityhall.model.entity.city​hall.Servant;
import com.foo.cityhall.service.BaseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("servants")
public class ServantController extends BaseController<Servant, ServantDTO, Integer> {

	public ServantController(BaseService<Servant, ServantDTO, Integer> service) {
		super(service);
	}

	@Override
	@PostMapping
	public ServantDTO create(@RequestBody @Valid ServantDTO dto) {
		return service.create(dto);
	}
}
