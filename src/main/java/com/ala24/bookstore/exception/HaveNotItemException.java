package com.ala24.bookstore.exception;

public class HaveNotItemException extends RuntimeException{
	public HaveNotItemException() {
		super();
	}

	public HaveNotItemException(String message) {
		super(message);
	}

	public HaveNotItemException(String message, Throwable cause) {
		super(message, cause);
	}

	public HaveNotItemException(Throwable cause) {
		super(cause);
	}
}
