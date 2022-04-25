package com.ala24.bookstore.domain;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.domain.orders.OrderItem;
import com.ala24.bookstore.repository.member.MemberRepository;
import com.ala24.bookstore.repository.orders.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderTest {

	private static Member member;
	private static Order order;
	private static Delivery delivery;
	private static OrderItem orderItem;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@BeforeEach
	void init() {
		member = Member.builder()
				.name("사나")
				.address(new Address())
				.cash(Cash.charge(0L))
				.build();

		orderItem = OrderItem.builder()
				.build();

		order = Order.builder()
				.member(member)
				.orderItem(Arrays.asList(orderItem))
				.delivery(delivery)
				.build();

		delivery = Delivery.builder()
				.address(new Address())
				.order(order)
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