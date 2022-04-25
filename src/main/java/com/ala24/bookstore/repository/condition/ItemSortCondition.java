package com.ala24.bookstore.repository.condition;

import lombok.Getter;

@Getter
public enum ItemSortCondition {
	PRICE("가격");

	private final String description;

	ItemSortCondition(String description) {
		this.description = description;
	}
}
