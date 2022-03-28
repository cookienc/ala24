package com.ala24.bookstore.web.session;

import lombok.Getter;

@Getter
public enum SessionName {

	LOGIN_MEMBER("loginMember");

	private final String name;
	private SessionName(String name) {
		this.name = name;
	}
}
