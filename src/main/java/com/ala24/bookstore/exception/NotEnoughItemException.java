package com.ala24.bookstore.exception;

/**
 * 상품이 없을 때 발생하는 예외 클래스
 */
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
