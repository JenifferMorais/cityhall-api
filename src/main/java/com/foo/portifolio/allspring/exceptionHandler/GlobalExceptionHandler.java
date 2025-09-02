package com.foo.portifolio.allspring.exceptionHandler;

import java.time.Instant;
import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.foo.portifolio.allspring.exception.BusinessException;
import com.foo.portifolio.allspring.security.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e, HttpServletRequest req) {
		HttpStatus status = "credenciais inválidas".equalsIgnoreCase(e.getMessage()) ? HttpStatus.UNAUTHORIZED
				: HttpStatus.UNPROCESSABLE_ENTITY; 
		return response(status, e.getMessage(), req);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustom(CustomException e, HttpServletRequest req) {
		HttpStatus status = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.BAD_REQUEST;
		return response(status, e.getMessage(), req);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException e, HttpServletRequest req) {
		return response(HttpStatus.NOT_FOUND, "recurso não encontrado", req);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e, HttpServletRequest req) {
		String msg = e.getBindingResult().getFieldErrors().stream().findFirst()
				.map(fe -> fe.getField() + ": " + fe.getDefaultMessage()).orElse("requisição inválida");
		return response(HttpStatus.BAD_REQUEST, msg, req);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e, HttpServletRequest req) {
		return response(HttpStatus.FORBIDDEN, "acesso negado", req);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(Exception e, HttpServletRequest req) {
		return response(HttpStatus.INTERNAL_SERVER_ERROR, "erro interno", req);
	}

	private ResponseEntity<ErrorResponse> response(HttpStatus status, String message, HttpServletRequest req) {
		var body = new ErrorResponse(message, status.value(), req.getRequestURI(), Instant.now());
		return ResponseEntity.status(status).body(body);
	}
}
