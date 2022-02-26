package com.ala24.bookstore.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(length = 10, nullable = false)
	private String name;

	@Embedded
	private Address address;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cash_id")
	private Cash cash;

	@Builder
	private Member(String name, Address address, Cash cash) {
		this.name = name;
		this.address = address;
		this.cash = cash;
	}


	public Long remainCash() {
		return this.cash.all();
	}

	public void charge(Long cash) {
		if (this.cash == null) {
			this.cash = Cash.charge(cash);
			return;
		}

		Long leftMoney = this.cash.all();
		this.cash = Cash.charge(leftMoney + cash);
	}
}
