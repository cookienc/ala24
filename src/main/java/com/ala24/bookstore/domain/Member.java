package com.ala24.bookstore.domain;

import com.ala24.bookstore.domain.type.MemberStatus;
import com.sun.istack.NotNull;
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

	@NotNull
	@Column(unique = true)
	private String loginId;

	@Column(length = 10, nullable = false)
	private String name;

	@Embedded
	private Address address;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cash_id")
	private Cash cash;

	@Enumerated(EnumType.STRING)
	private MemberStatus authority;

	@Builder
	private Member(String name, String loginId, Address address, Cash cash) {
		this.name = name;
		this.loginId = loginId;
		this.address = address;
		this.cash = cash;
		this.authority = MemberStatus.USER;
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
