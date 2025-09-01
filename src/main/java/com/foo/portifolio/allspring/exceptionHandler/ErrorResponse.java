package com.foo.portifolio.allspring.exceptionHandler;

import java.time.Instant;

public class ErrorResponse {

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String error) {
		super();
		this.error = error;
	}

	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Instant getTimestamp() {
		return Instant.now();
	}
}
