package com.ala24.bookstore.domain.item.condition;

import lombok.Getter;

/**
 * 검색 기색을 나타내는 enum 클래스
 */
@Getter
public enum ItemSearchCondition {
	NORMAL("분류"), NAME("이름"),
	AUTHOR("저자"), PUBLISHER("출판사");

	private String description;

	ItemSearchCondition(String description) {
		this.description = description;
	}
}
