package com.ala24.bookstore.exception;

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
