package com.ala24.bookstore.service;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.*;
import com.ala24.bookstore.exception.NotEnoughCashException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CashServiceTest {

	@Autowired
	CashService cashService;

	@Autowired
	MemberService memberService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	private static Member memberA;
	private static Member memberB;
	private static Member testMember;
	private static Order order;
	private static Delivery delivery;
	private static OrderItem orderItem;
	public static final long ZERO = 0L;
	private static Cash memberACash;
	private static Cash memberBCash;

	@BeforeEach
	void init() {

		memberACash = Cash.charge(ZERO);
		memberBCash = Cash.charge(ZERO);

		Address address = Address.builder()
				.city("서울")
				.specificAddress("은마아파트")
				.zipcode(22222)
				.build();

		memberA = Member.builder()
				.name("사나")
				.loginId("sana")
				.address(address)
				.cash(memberACash)
				.build();

		memberB = Member.builder()
				.name("다현")
				.loginId("dahyun")
				.address(address)
				.cash(memberBCash)
				.build();

		testMember = Member.builder()
				.name("사나")
				.loginId("sana")
				.address(address)
				.cash(memberACash)
				.build();
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 돈_충전_테스트() {
		//given
		Long memberAId = memberService.join(memberA);
		cashService.charge(memberAId, 10000L);
		//when
		Member findMember = memberService.findOne(memberAId);

		//then
		assertThat(findMember.account()).isEqualTo(10000L);
	}

	@ParameterizedTest
	@CsvSource(value = {"20000,10000,10000", "10001,10000,1", "1,0,1"})
	void 지불_가능_테스트(Long money, Long price, Long left) {
	    //given
		Long memberAId = memberService.join(memberA);
		memberA.charge(money);

	    //when
		cashService.pay(memberAId, price);

	    //then
		assertThat(memberA.account()).isEqualTo(left);
	}

	@ParameterizedTest
	@CsvSource(value = {"10000,20000", "10000,100001", "0,1"})
	void 지불_불가능_테스트(Long money, Long price) {
	    //given
		Long memberAId = memberService.join(memberA);
		memberA.charge(money);

		//when
		//then
		assertThatThrownBy(() -> cashService.pay(memberAId, price))
				.isInstanceOf(NotEnoughCashException.class);
	}
}