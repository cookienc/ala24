package com.ala24.bookstore.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderItem_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private Long orderPrice;

	private int count;

	@Builder
	public OrderItem(Order order, Long orderPrice, int count) {
		this.order = order;
		this.orderPrice = orderPrice;
		this.count = count;
	}
}