package com.ala24.bookstore.domain;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.SelfDevelopment;
import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.domain.orders.OrderItem;
import com.ala24.bookstore.repository.OrderItemRepository;
import org.junit.jupiter.api.AfterEach;
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

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 주문_아이템_저장테스트() {
	    //given

		Item book = SelfDevelopment.builder()
				.name("JPA")
				.author("김영한")
				.publisher("에이콘 출판")
				.price(38700)
				.stockQuantity(100)
				.build();

		OrderItem orderItem = OrderItem.builder()
				.order(new Order())
				.item(book)
				.orderPrice(10000)
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
		assertThat(findOrderItem.getItem()).isEqualTo(savedOrderItem.getItem());
	}

}