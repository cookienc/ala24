package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.item.Novel;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

	private static Member test;
	private static Address address;
	private static Novel novel;
	private static LinkedMultiValueMap<String, String> params;

	@Autowired
	MockMvc mvc;

	@Autowired
	MemberService memberService;

	@Autowired
	ItemService itemService;

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
	void 주문_폼으로_이동() throws Exception {
		//given
		memberService.join(test);
		Long itemId = itemService.saveItem(novel);
		//when
		mvc.perform(get("/order/" + itemId)
						.sessionAttr("loginMember", test))
				//then
				.andExpect(status().isOk())
				.andExpect(view().name("order/orderForm"));
	}

	@Test
	void 주문_성공() throws Exception {
		//given
		Long memberId = memberService.join(test);
		test.charge(100_000L);
		Long left = memberService.findOne(memberId).getCash().left();
		Long itemId = itemService.saveItem(novel);

		params = new LinkedMultiValueMap<>();
		params.add("itemId", String.valueOf(itemId));
		params.add("memberId", String.valueOf(memberId));
		params.add("price", "14670");
		params.add("quantity", String.valueOf(1));

		//when
		mvc.perform(post("/order")
						.sessionAttr("loginMember", test)
						.params(params))
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		Assertions.assertThat(memberService.findOne(memberId).getCash().left()).isEqualTo(100_000 - 14670);
	}
}