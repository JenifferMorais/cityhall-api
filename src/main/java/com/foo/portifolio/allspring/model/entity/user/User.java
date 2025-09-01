package com.foo.portifolio.allspring.model.entity.user;

import com.foo.portifolio.allspring.model.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user", schema = "auth")
public class User extends BaseEntity<Integer> {

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
