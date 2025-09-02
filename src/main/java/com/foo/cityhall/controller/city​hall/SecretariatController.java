package com.foo.cityhall.controller.city​hall;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foo.cityhall.controller.BaseController;
import com.foo.cityhall.model.dto.city​hall.SecretariatDTO;
import com.foo.cityhall.model.entity.city​hall.Secretariat;
import com.foo.cityhall.service.BaseService;

@RestController
@RequestMapping("secretariats")
public class SecretariatController extends BaseController<Secretariat, SecretariatDTO, Integer>{

	public SecretariatController(BaseService<Secretariat, SecretariatDTO, Integer> service) {
		super(service);
	}

}
