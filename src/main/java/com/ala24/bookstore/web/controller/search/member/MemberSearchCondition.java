package com.ala24.bookstore.web.controller.search.member;

import lombok.Getter;

/**
 * 회원 검색 기준을 나타내는 enum 클래스
 */
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
