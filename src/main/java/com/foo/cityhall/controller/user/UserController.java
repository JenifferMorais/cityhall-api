package com.foo.cityhall.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foo.cityhall.controller.BaseController;
import com.foo.cityhall.model.dto.user.UserDTO;
import com.foo.cityhall.model.entity.user.User;
import com.foo.cityhall.repository.auth.UserRepository;
import com.foo.cityhall.service.BaseService;

@RestController
@RequestMapping("users")
public class UserController extends BaseController<User, UserDTO, Integer> {
	public UserRepository repository;

	public UserController(BaseService<User, UserDTO, Integer> service) {
		super(service);
	}


}
