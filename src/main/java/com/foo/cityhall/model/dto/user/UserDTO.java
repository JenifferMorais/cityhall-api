package com.foo.cityhall.model.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foo.cityhall.model.dto.BaseEntityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
public class UserDTO extends BaseEntityDTO<Integer> {

	private Integer id;
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @ToString.Exclude
	private String password;
}
