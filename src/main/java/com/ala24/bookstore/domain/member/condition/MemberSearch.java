package com.ala24.bookstore.domain.member.condition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberSearch {

	private MemberSearchCondition condition;
	private String data;
}
