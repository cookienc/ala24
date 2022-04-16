package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.item.Novel;
import com.ala24.bookstore.service.CashService;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderCancelControllerTest {

	private static Member test;
	private static Address address;
	private static Novel novel;

	@Autowired
	MockMvc mvc;

	@Autowired
	MemberService memberService;

	@Autowired
	CashService cashService;

	@Autowired
	ItemService itemService;

	@Autowired
	OrderService orderService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void setUp() {
		address = Address.builder()
				.zipcode(12345)
				.city("Seoul")
				.specificAddress("Apartment")
				.build();

		test = Member.builder()
				.name("test")
				.loginId("test")
				.password("test")
				.cash(Cash.charge(0L))
				.address(address)
				.build();
		novel = Novel.builder()
				.name("기사단장죽이기1")
				.author("무라카미 하루키")
				.publisher("문학동네")
				.price(14670)
				.stockQuantity(800)
				.build();
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 주문_취소_성공() throws Exception {
	    //given
		Long memberId = memberService.join(test);

		cashService.charge(memberId, 100_000L);

		Long itemId = itemService.saveItem(novel);
		Long orderId = orderService.order(memberId, itemId, 2);

		//when
		mvc.perform(post("/order/" + orderId + "/cancel")
						.sessionAttr("loginMember", test))
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

	}
	@Test
	void 등록하지_않은_주문_취소_실행i() throws Exception {
		//given
		Long memberId = memberService.join(test);

		cashService.charge(memberId, 100_000L);

		Long itemId = itemService.saveItem(novel);
		Long orderId = orderService.order(memberId, itemId, 2);

		//when
		assertThatThrownBy(() -> mvc.perform(post("/order/" + orderId + 100 + "/cancel")
						.sessionAttr("loginMember", test))

		//then
				.andReturn()).hasCauseInstanceOf(NoSuchElementException.class);


	}

}