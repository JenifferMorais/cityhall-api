package com.foo.portifolio.allspring.controller.city​hall;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foo.portifolio.allspring.controller.BaseController;
import com.foo.portifolio.allspring.model.dto.city​hall.StateDTO;
import com.foo.portifolio.allspring.model.entity.city​hall.State;
import com.foo.portifolio.allspring.service.BaseService;

@RestController
@RequestMapping("state")
public class StateController extends BaseController<State, StateDTO, Integer>{

	public StateController(BaseService<State, StateDTO, Integer> service) {
		super(service);
	}

}
