package com.ala24.bookstore.domain;

import com.ala24.bookstore.repository.MemberRepository;
import com.ala24.bookstore.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderTest {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	OrderRepository orderRepository;

	@Test
	@Transactional
	void 주문_생성_테스트() {
	    //given
		Member memberA = Member.builder()
				.name("사나")
				.address(new Address())
				.cash(Cash.charge(0L))
				.build();

		Order order = Order.builder()
				.member(memberA)
				.build();

		Order savedOrder = orderRepository.save(order);

		//when
		Order findOrder = orderRepository.findById(savedOrder.getId()).get();

		//then
		Assertions.assertThat(findOrder.getMember()).isEqualTo(savedOrder.getMember());
		Assertions.assertThat(findOrder.getOrderDate()).isEqualTo(savedOrder.getOrderDate());
	}
}