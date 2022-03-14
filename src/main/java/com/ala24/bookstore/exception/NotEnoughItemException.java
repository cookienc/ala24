package com.ala24.bookstore.exception;

public class NotEnoughItemException extends RuntimeException{
	public NotEnoughItemException() {
		super();
	}

	public NotEnoughItemException(String message) {
		super(message);
	}

	public NotEnoughItemException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughItemException(Throwable cause) {
		super(cause);
	}
}
