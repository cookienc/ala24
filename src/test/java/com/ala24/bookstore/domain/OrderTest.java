package com.ala24.bookstore.domain;

import com.ala24.bookstore.repository.MemberRepository;
import com.ala24.bookstore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderTest {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	OrderRepository orderRepository;

	private static Member member;
	private static Order order;
	private static Delivery delivery;

	@BeforeEach
	void init() {
		member = Member.builder()
				.name("사나")
				.address(new Address())
				.cash(Cash.charge(0L))
				.build();

		delivery = Delivery.builder()
				.address(new Address())
				.order(new Order())
				.build();

		order = Order.builder()
				.member(member)
				.delivery(delivery)
				.delivery(new Delivery())
				.build();
	}
	@Test
	void 주문_생성_테스트() {
	    //given
		Order savedOrder = orderRepository.save(order);

		//when
		Order findOrder = orderRepository.findById(savedOrder.getId()).get();

		//then
		assertThat(findOrder.getMember()).isEqualTo(savedOrder.getMember());
		assertThat(findOrder.getOrderDate()).isEqualTo(savedOrder.getOrderDate());
		assertThat(findOrder.getDelivery()).isEqualTo(savedOrder.getDelivery());
	}
}