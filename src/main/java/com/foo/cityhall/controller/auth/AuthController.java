package com.foo.cityhall.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foo.cityhall.model.dto.auth.AuthDTO;
import com.foo.cityhall.model.dto.auth.AuthResponse;
import com.foo.cityhall.model.dto.auth.TokenValidationResponse;
import com.foo.cityhall.service.auth.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping
	public ResponseEntity<AuthResponse> login(@RequestBody AuthDTO dto) {
		return ResponseEntity.ok(authService.login(dto));
	}

	@Operation(security = { @SecurityRequirement(name = "bearerAuth") })
	@PostMapping("/validate")
	public ResponseEntity<TokenValidationResponse> validate(HttpServletRequest req) {
	    return ResponseEntity.ok(authService.validateToken(req));
	}
}
