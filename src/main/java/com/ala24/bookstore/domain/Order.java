package com.ala24.bookstore.domain;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.type.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ala24.bookstore.domain.type.DeliveryStatus.*;
import static com.ala24.bookstore.domain.type.OrderStatus.*;
import static com.ala24.bookstore.exception.utils.Sentence.ALREADY_CANCELD;
import static com.ala24.bookstore.exception.utils.Sentence.NOW_DELIVERING;

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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
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
		this.orderStatus = ORDER;
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

	public void addDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public void cancel() {
		if (isItDelivering()) {
			throw new IllegalStateException(NOW_DELIVERING.toString());
		}

		if (isItAlreadyCancel()) {
			throw new IllegalStateException(ALREADY_CANCELD.toString());
		}

		this.delivery.cancel();

		this.orderStatus = OrderStatus.CANCEL;
		for (OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}

	private boolean isItAlreadyCancel() {
		return this.orderStatus == OrderStatus.CANCEL;
	}

	private boolean isItDelivering() {
		return this.delivery.getDeliveryStatus() == SHIPPING
				|| this.delivery.getDeliveryStatus() == COMPLETE;
	}
}
