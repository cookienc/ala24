package com.ala24.bookstore.domain.orders.condition;

import lombok.Getter;

@Getter
public enum OrderSearchCondition {
	NORMAL("검색"), MEMBER_NAME("주문자명"), DELIVERY_STATUS("배송상태"), ITEM_NAME("상품이름");

	private final String description;

	OrderSearchCondition(String description) {
		this.description = description;
	}
}
