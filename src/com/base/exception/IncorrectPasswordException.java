package com.base.exception;

import org.apache.shiro.authc.AuthenticationException;

public class IncorrectPasswordException extends AuthenticationException{
	private static final long serialVersionUID = 1L;

	public IncorrectPasswordException() {
		super();
	}

	public IncorrectPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectPasswordException(String message) {
		super(message);
	}

	public IncorrectPasswordException(Throwable cause) {
		super(cause);
	}
}
