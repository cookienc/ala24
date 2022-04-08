package com.ala24.bookstore.domain.type;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
	CANCEL("주문 취소"), PREPARING("배송 준비"), SHIPPING("배송 중"), COMPLETE("배달 완료");

	private String description;

	DeliveryStatus(String description) {
		this.description = description;
	}
}
