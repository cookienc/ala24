package com.ala24.bookstore.exception;

/**
 * 돈이 부족할 때 발생하는 예외 클래스
 */
public class NotEnoughCashException extends RuntimeException{

	public NotEnoughCashException() {
	}

	public NotEnoughCashException(String message) {
		super(message);
	}

	public NotEnoughCashException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughCashException(Throwable cause) {
		super(cause);
	}
}
