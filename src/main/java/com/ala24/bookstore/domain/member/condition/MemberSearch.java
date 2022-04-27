package com.ala24.bookstore.domain.member.condition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 회원 조건을 나타내는 클래스
 */
@Getter
@Setter
@RequiredArgsConstructor
public class MemberSearch {

	private MemberSearchCondition condition; //회원 검색 기준
	private String data; //검색 내용
}
