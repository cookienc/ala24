package com.ala24.bookstore.domain;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.exception.NotEnoughItemException;
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private Order order;

	private int orderPrice;

	private int count;

	@Builder
	private OrderItem(Item item, Order order, int orderPrice, int count) {
		this.item = item;
		this.order = order;
		this.orderPrice = orderPrice;
		this.count = count;
	}

	public void addOrder(Order order) {
		this.order = order;
	}

	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {

		validateItem(item, count);

		return OrderItem.builder()
				.item(item)
				.orderPrice(orderPrice)
				.count(count)
				.build();
	}

	public void cancel() {
		item.addStock(this.count);
	}

	private static void validateItem(Item item, int count) {
		if (item.getStockQuantity() < count) {
			throw new NotEnoughItemException("상품의 재고가 부족합니다.");
		}
	}
}
