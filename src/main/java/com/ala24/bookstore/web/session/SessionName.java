package com.ala24.bookstore.web.session;

import lombok.Getter;

/**
 * 로그인 세션의 이름을 나타내는 enum class
 */
@Getter
public enum SessionName {

	LOGIN_MEMBER("loginMember");

	private final String name;
	private SessionName(String name) {
		this.name = name;
	}
}
