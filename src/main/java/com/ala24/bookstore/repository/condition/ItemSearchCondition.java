package com.ala24.bookstore.repository.condition;

import lombok.Getter;

@Getter
public enum ItemSearchCondition {
	NAME("이름");

	private String description;

	ItemSearchCondition(String description) {
		this.description = description;
	}
}
