package com.foo.cityhall.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds ;

	@Autowired
	private MyUserDetails myUserDetails;

	private SecretKey key() {
		  return io.jsonwebtoken.security.Keys.hmacShaKeyFor(
		    io.jsonwebtoken.io.Decoders.BASE64.decode(secretKey)
		  );
		}

	public String createToken(String subject) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + validityInMilliseconds);
		return Jwts.builder().setSubject(subject)
				.setIssuedAt(now).setExpiration(exp).signWith(key(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(key()).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new CustomException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
		}
	}

	public String getEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest req) {
		String bearer = req.getHeader("Authorization");
		return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
	}

	public Authentication getAuthentication(String token) {
		UserDetails ud = myUserDetails.loadUserByUsername(getEmail(token));
		return new UsernamePasswordAuthenticationToken(ud, "", ud.getAuthorities());
	}

	public io.jsonwebtoken.Claims getClaims(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(key())
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	}


	public String extractBearerOrThrow(HttpServletRequest req) {
	    String h = req.getHeader("Authorization");
	    if (h == null || h.isBlank()) {
			throw new CustomException("Cabeçalho Authorization ausente", HttpStatus.BAD_REQUEST);
		}
	    if (!h.startsWith("Bearer ")) {
			throw new CustomException("Authorization deve usar esquema Bearer", HttpStatus.BAD_REQUEST);
		}
	    String token = h.substring(7).trim();
	    if (token.isEmpty()) {
			throw new CustomException("Token Bearer vazio", HttpStatus.BAD_REQUEST);
		}
	    return token;
	}


	public Claims parseClaimsOrThrow(String token) {
	    try {
	        return Jwts.parserBuilder()
	                .setSigningKey(key())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    } catch (ExpiredJwtException e) {
	        throw new CustomException("Token expirado", HttpStatus.UNAUTHORIZED);
	    } catch (io.jsonwebtoken.security.SecurityException e) {
	        throw new CustomException("Assinatura do token inválida", HttpStatus.UNAUTHORIZED);
	    } catch (MalformedJwtException | UnsupportedJwtException e) {
	        throw new CustomException("Token JWT malformado ou não suportado", HttpStatus.UNAUTHORIZED);
	    } catch (IllegalArgumentException e) {
	        throw new CustomException("Token ausente ou inválido", HttpStatus.BAD_REQUEST);
	    }
	}

}