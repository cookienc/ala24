package com.ala24.bookstore.web.controller.item;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.SelfDevelopment;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemUpdateControllerTest {

	private static Item book;
	private static Member test;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ItemService itemService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void setUp() {
		test = Member.builder()
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
		book = SelfDevelopment.builder()
				.name("JPA")
				.author("김영한")
				.publisher("에이콘 출판")
				.price(38700)
				.stockQuantity(100)
				.build();

		memberService.join(test);
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 상품_수정_폼_테스트() throws Exception {
	    //given
		Long itemId = itemService.saveItem(book);
		//when
		mvc.perform(get("/items/" + itemId + "/edit")
						.sessionAttr("loginMember", test))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("items/updateForm"));
	}

	@Test
	void 상품_수정_성공_테스트() throws Exception {
	    //given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("name", "JPA");
		params.add("author", "김영한");
		params.add("category", "NOVEL");
		params.add("publisher", "에이콘 출판");
		params.add("price", "38700");
		params.add("stockQuantity", "1000");

		Long itemId = itemService.saveItem(book);
		//when
		mvc.perform(post("/items/" + itemId + "/edit")
						.sessionAttr("loginMember", test)
						.params(params))
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/items/list"))
				.andDo(print());
	}
}