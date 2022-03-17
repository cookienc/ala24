package com.ala24.bookstore.exception.utils;

public enum Sentence {

	ALREADY_CANCELD("이미 취소된 주문입니다."),

	NO_ORDER("해당 주문은 없습니다."),
	NO_MEMBER("해당 회원은 존재하지 않습니다."),
	NO_ITEM("해당 아이템은 존재하지 않습니다."),

	NOT_DUPLICATE_MEMBER("중복된 아이디로 가입할 수 없습니다."),
	NOT_ENOUGH_CASH("계좌에 돈이 부족합니다."),

	NOW_DELIVERING("배숭 중인 상품은 취소할 수 없습니다.");

	private String explanation;

	private Sentence(String explanation) {
		this.explanation = explanation;
	}


	@Override
	public String toString() {
		return this.explanation;
	}
}
