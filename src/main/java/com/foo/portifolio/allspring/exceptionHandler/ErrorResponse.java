package com.foo.portifolio.allspring.exceptionHandler;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ErrorResponse {
	private String error;
	private int status;
	private String path;
	private Instant timestamp;

	public Instant getTimestamp() {
		return Instant.now();
	}
}
