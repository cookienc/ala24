package com.ala24.bookstore.web.controller.search.item;

import lombok.Getter;

/**
 * 상품의 정렬 기준을 나타내는 enum 클래스
 */
@Getter
public enum ItemSortCondition {
	PRICE("가격");

	private final String description;

	ItemSortCondition(String description) {
		this.description = description;
	}
}
