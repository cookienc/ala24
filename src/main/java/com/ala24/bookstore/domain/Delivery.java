package com.ala24.bookstore.domain;

import com.ala24.bookstore.domain.type.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")
	@JoinColumn(name = "order_id")
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;

	@Builder
	private Delivery(Order order, Address address) {
		this.order = order;
		this.address = address;
		this.deliveryStatus = DeliveryStatus.PREPARING;
	}
}
