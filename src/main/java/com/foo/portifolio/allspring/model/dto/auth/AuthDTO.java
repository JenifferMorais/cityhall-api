package com.foo.portifolio.allspring.model.dto.auth;

import com.foo.portifolio.allspring.model.dto.BaseEntityDTO;

import lombok.Data;

@Data
public class AuthDTO extends BaseEntityDTO<Integer> {
	private String username;
	private String password;
}
