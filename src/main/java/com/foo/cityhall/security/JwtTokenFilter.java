package com.foo.cityhall.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(req);

		if (token == null || token.isBlank()) {
			chain.doFilter(req, res);
			return;
		}

		try {
			if (jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (CustomException ex) {
			SecurityContextHolder.clearContext();
			throw ex;
		}

		chain.doFilter(req, res);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
	    String p = request.getServletPath();
	    return p.startsWith("/auth")   
	        || p.startsWith("/v3/api-docs")
	        || p.startsWith("/swagger-ui")
	        || "OPTIONS".equalsIgnoreCase(request.getMethod());
	}


}
