package com.ala24.bookstore.web.controller.search.order;

import lombok.Getter;

//주문 검색 기준을 나타내는 클래스
@Getter
public enum OrderSearchCondition {
	NORMAL("검색"), MEMBER_NAME("주문자명"), DELIVERY_STATUS("배송상태"), ITEM_NAME("상품이름");

	private final String description;

	OrderSearchCondition(String description) {
		this.description = description;
	}
}
