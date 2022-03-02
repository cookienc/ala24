package com.ala24.bookstore.domain;

import com.ala24.bookstore.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderItemTest {

	@Autowired
	OrderItemRepository orderItemRepository;

	@Test
	void 주문_아이템_저장테스트() {
	    //given
		OrderItem orderItem = OrderItem.builder()
				.order(new Order())
				.orderPrice(10000L)
				.count(3)
				.build();

		OrderItem savedOrderItem = orderItemRepository.save(orderItem);

		//when
		OrderItem findOrderItem = orderItemRepository.findById(savedOrderItem.getId())
				.orElseThrow(() -> new NoSuchElementException());

		//then
		assertThat(findOrderItem.getOrder()).isEqualTo(savedOrderItem.getOrder());
		assertThat(findOrderItem.getOrderPrice()).isEqualTo(10000L);
		assertThat(findOrderItem.getCount()).isEqualTo(3);
	}

}