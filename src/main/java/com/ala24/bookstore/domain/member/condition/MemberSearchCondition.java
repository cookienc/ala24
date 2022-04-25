package com.ala24.bookstore.domain.member.condition;

import lombok.Getter;

@Getter
public enum MemberSearchCondition {
	NORMAL("검색"), NAME("이름");

	private String description;

	MemberSearchCondition(String description) {
		this.description = description;
	}
}
