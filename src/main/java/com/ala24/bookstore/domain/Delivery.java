package com.ala24.bookstore.domain;

import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.domain.type.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.ala24.bookstore.domain.type.DeliveryStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery", cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;

	@Builder
	private Delivery(Order order, Address address) {
		this.address = address;
		this.deliveryStatus = PREPARING;
		addOrder(order);
	}

	private Delivery(Address address) {
		this.address = address;
		this.deliveryStatus = PREPARING;
	}

	public static Delivery createDelivery(Address address) {
		return new Delivery(address);
	}

	public void addOrder(Order order) {
		this.order = order;
		order.addDelivery(this);
	}

	public void cancel() {
		this.deliveryStatus = CANCEL;
	}
}
