package com.foo.cityhall.model.dto.auth;

import com.foo.cityhall.model.dto.BaseEntityDTO;

import lombok.Data;

@Data
public class AuthDTO extends BaseEntityDTO<Integer> {
	private String username;
	private String password;
}
