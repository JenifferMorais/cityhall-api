package com.foo.portifolio.allspring.controller.city​hall;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foo.portifolio.allspring.controller.BaseController;
import com.foo.portifolio.allspring.model.dto.city​hall.SecretariatDTO;
import com.foo.portifolio.allspring.model.entity.city​hall.Secretariat;
import com.foo.portifolio.allspring.service.BaseService;

@RestController
@RequestMapping("secretariats")
public class SecretariatController extends BaseController<Secretariat, SecretariatDTO, Integer>{

	public SecretariatController(BaseService<Secretariat, SecretariatDTO, Integer> service) {
		super(service);
	}

}
