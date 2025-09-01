package com.foo.portifolio.allspring.exceptionHandler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.foo.portifolio.allspring.exception.BusinessException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({NoSuchElementException.class})
	public ResponseEntity<ErrorResponse> unkonwError(NoSuchElementException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase()));
	}
	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> unkonwError(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
	}
	@ExceptionHandler({BusinessException.class})
	public ResponseEntity<ErrorResponse> businessError(BusinessException e){
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(new ErrorResponse(e.getMessage()));
	}

}
