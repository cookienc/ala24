package com.ala24.bookstore.domain;

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
}
