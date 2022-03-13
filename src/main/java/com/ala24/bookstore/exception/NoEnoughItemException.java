package com.ala24.bookstore.exception;

public class NoEnoughItemException extends RuntimeException{
	public NoEnoughItemException() {
		super();
	}

	public NoEnoughItemException(String message) {
		super(message);
	}

	public NoEnoughItemException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoEnoughItemException(Throwable cause) {
		super(cause);
	}
}
