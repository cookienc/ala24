package com.ala24.bookstore.web.dtos.orderdto;

import com.ala24.bookstore.domain.Delivery;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.domain.orders.OrderItem;
import com.ala24.bookstore.domain.type.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderListDto {

	private Long orderId;
	private List<OrderItem> orderItems;
	private Delivery delivery;
	private Member member;
	private LocalDateTime orderDate;
	private OrderStatus orderStatus;

	public OrderListDto(Order order) {
		this.orderId = order.getId();
		this.orderItems = order.getOrderItems();
		this.delivery = order.getDelivery();
		this.member = order.getMember();
		this.orderDate = order.getOrderDate();
		this.orderStatus = order.getOrderStatus();
	}
}
