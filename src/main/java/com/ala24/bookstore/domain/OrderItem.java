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
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	Order order;

	Long orderPrice;

	int count;

	@Builder
	public OrderItem(Order order, Long orderPrice, int count) {
		this.order = order;
		this.orderPrice = orderPrice;
		this.count = count;
	}
}
