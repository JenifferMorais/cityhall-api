package com.foo.cityhall.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foo.cityhall.exception.BusinessException;
import com.foo.cityhall.model.dto.auth.AuthDTO;
import com.foo.cityhall.model.dto.auth.AuthResponse;
import com.foo.cityhall.model.dto.auth.TokenValidationResponse;
import com.foo.cityhall.model.entity.user.User;
import com.foo.cityhall.model.mapper.AuthMapper;
import com.foo.cityhall.security.JwtTokenProvider;
import com.foo.cityhall.service.user.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthMapper authMapper;

	public AuthResponse login(AuthDTO request) {
	    User creds = authMapper.toEntity(request);
	    User persisted = userService.getByUsernameOrThrow(creds.getUsername());

	    if (!passwordEncoder.matches(request.getPassword(), persisted.getPassword())) {
			throw new BusinessException("credenciais inválidas");
		}

	    String token = jwtTokenProvider.createToken(persisted.getUsername());
	    var userInfo = new AuthResponse.UserInfo(persisted.getId(), persisted.getUsername());

	    return new AuthResponse(token, "Bearer", userInfo);
	}

	public TokenValidationResponse validateToken(HttpServletRequest req) {
	    String token = jwtTokenProvider.extractBearerOrThrow(req);
	    Claims c = jwtTokenProvider.parseClaimsOrThrow(token);
	    var user = userService.getByUsernameOrThrow(c.getSubject());
	    return new TokenValidationResponse(
	        true,
	        user.getId(),
	        user.getUsername(),
	        c.getIssuedAt() != null ? c.getIssuedAt().getTime() : null,
	        c.getExpiration() != null ? c.getExpiration().getTime() : null
	    );
	}

}
