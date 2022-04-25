package com.ala24.bookstore.domain.orders.condition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

	private OrderSearchCondition condition;
	private String data;
}
