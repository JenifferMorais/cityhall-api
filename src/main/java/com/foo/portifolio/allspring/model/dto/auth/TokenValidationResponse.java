package com.foo.portifolio.allspring.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationResponse {
    private boolean valid;
    private Integer id;
    private String username;
    private Long issuedAt;
    private Long expiresAt;
}
