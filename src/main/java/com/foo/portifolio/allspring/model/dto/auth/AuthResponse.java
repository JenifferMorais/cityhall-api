package com.foo.portifolio.allspring.model.dto.auth;

import com.foo.portifolio.allspring.model.dto.BaseEntityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	private String token;
	private String tokenType;
	private UserInfo user;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UserInfo {
		private Integer id;
		private String username;
	}
}
