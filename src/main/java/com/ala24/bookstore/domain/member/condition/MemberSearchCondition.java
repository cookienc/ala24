package com.ala24.bookstore.domain.member.condition;

import lombok.Getter;

@Getter
public enum MemberSearchCondition {
	NORMAL("검색"), NAME("이름"), LOGIN_ID("아이디"),
	CITY("도시"), ZIPCODE("우편번호"), ADDRESS("주소"),
	AUTHORITY("권한");

	private String description;

	MemberSearchCondition(String description) {
		this.description = description;
	}
}
