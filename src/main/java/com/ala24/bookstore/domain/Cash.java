package com.ala24.bookstore.domain;

import com.ala24.bookstore.domain.orders.OrderItem;
import com.ala24.bookstore.exception.NotEnoughCashException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cash {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cash_id")
	private Long id;

	private Long money;

	public static Cash charge(Long cash) {
		return new Cash(cash);
	}

	private Cash(Long cash) {
		this.money = cash;
	}

	public Long left() {
		return this.money;
	}

	public void validate(OrderItem item) {
		int totalMoney = item.getCount() * item.getOrderPrice();

		if (this.money < totalMoney) {
			throw new NotEnoughCashException("돈이 부족합니다.");
		}

		pay(totalMoney);
	}

	private Long pay(int totalMoney) {
		this.money -= totalMoney;
		return this.money;
	}
}
