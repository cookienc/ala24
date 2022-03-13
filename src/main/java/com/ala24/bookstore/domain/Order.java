package com.ala24.bookstore.domain;

import com.ala24.bookstore.domain.type.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();

	private LocalDateTime orderDate;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Builder
	public Order(Member member, Delivery delivery, List<OrderItem> orderItem) {
		this.member = member;
		this.delivery = delivery;
		this.orderDate = LocalDateTime.now();
		this.orderStatus = OrderStatus.ORDER;
	}

	private void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
		orderItem.addOrder(this);
	}

	public static Order createOrder(Member member, Delivery delivery, OrderItem orderItem) {
		Order order = Order.builder()
				.member(member)
				.delivery(delivery)
				.build();

		order.addOrderItem(orderItem);
		delivery.addOrder(order);
		return order;
	}
}
