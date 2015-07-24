package com.base.exception;

/**
 * Service层公用的Exception.
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * 只有跑车RuntimeException类型才会触发回滚
 */
public class SpringException extends RuntimeException {
	private static final long serialVersionUID = -7196784301399621096L;
	public SpringException() {
		super();
	}
	public SpringException(String message) {
		super(message);
	}

	public SpringException(Throwable cause) {
		super(cause);
	}

	public SpringException(String message, Throwable cause) {
		super(message, cause);
	}
}
