package com.foo.portifolio.allspring.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foo.portifolio.allspring.controller.BaseController;
import com.foo.portifolio.allspring.model.dto.user.UserDTO;
import com.foo.portifolio.allspring.model.entity.user.User;
import com.foo.portifolio.allspring.repository.auth.UserRepository;
import com.foo.portifolio.allspring.service.BaseService;

@RestController
@RequestMapping("users")
public class UserController extends BaseController<User, UserDTO, Integer> {
	public UserRepository repository;
	
	public UserController(BaseService<User, UserDTO, Integer> service) {
		super(service);
	}


}
