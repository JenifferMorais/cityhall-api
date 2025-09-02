package com.foo.cityhall.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foo.cityhall.model.dto.BaseEntityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
public class AuthRequest extends BaseEntityDTO<Integer>{
    @NotBlank
    private String username;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @ToString.Exclude
    private String password;
}
