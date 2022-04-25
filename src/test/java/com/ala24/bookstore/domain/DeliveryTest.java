package com.ala24.bookstore.domain;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.repository.DeliveryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DeliveryTest {

	@Autowired
	DeliveryRepository deliveryRepository;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 배송_생성_테스트() {
	    //given
		Address address = Address.builder()
				.city("서울")
				.zipcode(12345)
				.specificAddress("은마아파트")
				.build();

		Member test = Member.builder()
				.name("사나")
				.address(address)
				.cash(Cash.charge(1000L))
				.build();

		Delivery delivery = Delivery.builder()
				.address(address)
				.order(new Order())
				.build();

		Delivery savedDelivery = deliveryRepository.save(delivery);

		//when
		Delivery findDelivery = deliveryRepository.findById(savedDelivery.getId())
				.orElseThrow(() -> new NoSuchElementException());

		//then
		assertThat(findDelivery.getAddress()).isEqualTo(savedDelivery.getAddress());
		assertThat(findDelivery.getOrder()).isEqualTo(savedDelivery.getOrder());
		assertThat(findDelivery).isEqualTo(savedDelivery);
	}
}