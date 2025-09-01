package com.foo.portifolio.allspring.model.dto.todo;

import com.foo.portifolio.allspring.model.dto.BaseEntityDTO;

public class StateDTO extends BaseEntityDTO<Integer>{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
