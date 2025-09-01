package com.foo.portifolio.allspring.model.dto.user;

import com.foo.portifolio.allspring.model.dto.BaseEntityDTO;

public class UserDTO extends BaseEntityDTO<Integer> {
	private String password;
	private String username;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
