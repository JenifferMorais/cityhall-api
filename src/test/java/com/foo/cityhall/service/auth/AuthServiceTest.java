package com.foo.cityhall.service.auth;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UserService userService;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtTokenProvider jwtTokenProvider;
    @Mock AuthMapper authMapper;

    private AuthService svc() {
        return new AuthService(userService, passwordEncoder, jwtTokenProvider, authMapper);
    }

    @Test
    void login_Success_ReturnsTokenAndUserInfo() {
        AuthService service = svc();

        AuthDTO req = new AuthDTO();
        req.setUsername("alice");
        req.setPassword("raw");

        User mapped = new User(); mapped.setUsername("alice");
        User persisted = new User(); persisted.setId(10); persisted.setUsername("alice"); persisted.setPassword("ENC");

        when(authMapper.toEntity(req)).thenReturn(mapped);
        when(userService.getByUsernameOrThrow("alice")).thenReturn(persisted);
        when(passwordEncoder.matches("raw", "ENC")).thenReturn(true);
        when(jwtTokenProvider.createToken("alice")).thenReturn("jwt-123");

        AuthResponse out = service.login(req);

        assertNotNull(out);
        assertEquals("jwt-123", out.getToken());
        assertEquals("Bearer", out.getTokenType());
        assertNotNull(out.getUser());
        assertEquals(10, out.getUser().getId());
        assertEquals("alice", out.getUser().getUsername());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(jwtTokenProvider).createToken(captor.capture());
        assertEquals("alice", captor.getValue());
    }

    @Test
    void login_InvalidPassword_ThrowsBusinessException() {
        AuthService service = svc();

        AuthDTO req = new AuthDTO();
        req.setUsername("bob");
        req.setPassword("wrong");

        User mapped = new User(); mapped.setUsername("bob");
        User persisted = new User(); persisted.setUsername("bob"); persisted.setPassword("HASH");

        when(authMapper.toEntity(req)).thenReturn(mapped);
        when(userService.getByUsernameOrThrow("bob")).thenReturn(persisted);
        when(passwordEncoder.matches("wrong", "HASH")).thenReturn(false);

        assertThrows(BusinessException.class, () -> service.login(req));
        verify(jwtTokenProvider, never()).createToken(any());
    }

    @Test
    void validateToken_Success_ReturnsPayload() {
        AuthService service = svc();

        HttpServletRequest httpReq = mock(HttpServletRequest.class);
        when(jwtTokenProvider.extractBearerOrThrow(httpReq)).thenReturn("jwt-xyz");

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("alice");
        when(claims.getIssuedAt()).thenReturn(new Date(1111L));
        when(claims.getExpiration()).thenReturn(new Date(2222L));
        when(jwtTokenProvider.parseClaimsOrThrow("jwt-xyz")).thenReturn(claims);

        User persisted = new User(); persisted.setId(10); persisted.setUsername("alice");
        when(userService.getByUsernameOrThrow("alice")).thenReturn(persisted);

        TokenValidationResponse resp = service.validateToken(httpReq);

        assertNotNull(resp);
        assertTrue(resp.isValid());
        assertEquals(10, resp.getId());
        assertEquals("alice", resp.getUsername());
        assertEquals(1111L, resp.getIssuedAt());
        assertEquals(2222L, resp.getExpiresAt());
    }

    @Test
    void validateToken_AllowsNullDates() {
        AuthService service = svc();

        HttpServletRequest httpReq = mock(HttpServletRequest.class);
        when(jwtTokenProvider.extractBearerOrThrow(httpReq)).thenReturn("jwt-null");

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("carol");
        when(claims.getIssuedAt()).thenReturn(null);
        when(claims.getExpiration()).thenReturn(null);
        when(jwtTokenProvider.parseClaimsOrThrow("jwt-null")).thenReturn(claims);

        User persisted = new User(); persisted.setId(77); persisted.setUsername("carol");
        when(userService.getByUsernameOrThrow("carol")).thenReturn(persisted);

        TokenValidationResponse resp = service.validateToken(httpReq);

        assertTrue(resp.isValid());
        assertEquals(77, resp.getId());
        assertEquals("carol", resp.getUsername());
        assertNull(resp.getIssuedAt());
        assertNull(resp.getExpiresAt());
    }
}
