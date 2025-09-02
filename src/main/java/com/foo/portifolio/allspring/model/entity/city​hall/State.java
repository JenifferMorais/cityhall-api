package com.foo.portifolio.allspring.model.entity.city​hall;

import com.foo.portifolio.allspring.model.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "state", schema = "todos")
public class State extends BaseEntity<Integer> {

	public State() {
		super();
	}

	public State(int id) {
		super();
		super.setId(id);
	}

	@Column(name = "name", length = 60)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
