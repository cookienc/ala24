package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.item.Novel;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.dtos.orderdto.OrderFormDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
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

	private static Stream<Arguments> sampleData() {
		OrderFormDto orderForm = new OrderFormDto().toDto(novel, test);
		OrderFormDto noQuantity = new OrderFormDto().toDto(novel, test);
		noQuantity.setQuantity(0);
		OrderFormDto noCity = new OrderFormDto().toDto(novel, test);
		noCity.setCity("");
		OrderFormDto noSpecificAddress = new OrderFormDto().toDto(novel, test);
		noSpecificAddress.setSpecificAddress("");
		OrderFormDto noZipcode = new OrderFormDto().toDto(novel, test);
		noZipcode.setCity(null);


		return Stream.of(
				Arguments.of(orderForm, 0),
				Arguments.of(noQuantity, 1),
				Arguments.of(noCity, 1),
				Arguments.of(noSpecificAddress, 1),
				Arguments.of(noZipcode, 1)
		);
	}

	@Autowired
	MockMvc mvc;

	@Autowired
	Validator validator;

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
		params.add("memberName", test.getName());
		params.add("city", test.getAddress().getCity());
		params.add("specificAddress", test.getAddress().getSpecificAddress());
		params.add("zipcode", String.valueOf(test.getAddress().getZipcode()));
		params.add("stockQuantity", String.valueOf(novel.getStockQuantity()));
		params.add("itemName", novel.getName());
		params.add("price", String.valueOf(novel.getPrice()));
		params.add("quantity", String.valueOf(1));

		//when
		mvc.perform(post("/order")
						.sessionAttr("loginMember", test)
						.params(params))
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		assertThat(memberService.findOne(memberId).getCash().left()).isEqualTo(100_000 - 14670);
	}
	
	@ParameterizedTest
	@MethodSource("sampleData")
	void 검증_테스트(OrderFormDto orderForm, int count) {
	    //given
	    //when
		Set<ConstraintViolation<OrderFormDto>> violations = validator.validate(orderForm);
		//then
		assertThat(violations.size()).isEqualTo(count);
	}
}