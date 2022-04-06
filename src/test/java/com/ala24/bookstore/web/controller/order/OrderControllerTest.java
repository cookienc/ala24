package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.item.Novel;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	MemberService memberService;

	@Autowired
	ItemService itemService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 주문_폼으로_이동() throws Exception {
	    //given
		Member test = Member.builder()
				.name("test")
				.loginId("test")
				.password("test")
				.cash(Cash.charge(0L))
				.address(Address.builder()
						.zipcode(12345)
						.city("Seoul")
						.specificAddress("Apartment")
						.build())
				.build();
		Novel novel = Novel.builder()
				.name("기사단장죽이기1")
				.author("무라카미 하루키")
				.publisher("문학동네")
				.price(14670)
				.stockQuantity(800)
				.build();

		memberService.join(test);
		Long itemId = itemService.saveItem(novel);
		//when
		mvc.perform(get("/order/" + itemId)
						.sessionAttr("loginMember", test))
				//then
				.andExpect(status().isOk())
				.andExpect(view().name("order/orderForm"));
	}
}