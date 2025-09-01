package com.foo.portifolio.allspring.exception;

public class BusinessException extends RuntimeException {

	private int status = 500;
	private static final long serialVersionUID = 1L;

	public BusinessException(String message, Throwable cause, int status) {
		super(message, cause);
		this.status = status;
	}

	public BusinessException(String message, int status) {
		super(message);
		this.status = status;
	}

	public BusinessException(int status) {
		super();
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BusinessException() {
		super();

	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
