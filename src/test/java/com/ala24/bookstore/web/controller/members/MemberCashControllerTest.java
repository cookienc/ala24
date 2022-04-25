package com.ala24.bookstore.web.controller.members;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.dtos.memberdto.CashFormDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberCashControllerTest {

	public static final long CHARGE_MONEY = 10000L;
	private static Member testMember;

	@Autowired
	MockMvc mvc;

	@Autowired
	Validator validator;

	@Autowired
	MemberService memberService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void setUp() {
		testMember = Member.builder()
				.name("test")
				.loginId("test")
				.password("1234")
				.cash(Cash.charge(0L))
				.address(Address.builder()
						.city("서울")
						.specificAddress("강남")
						.zipcode(12345)
						.build()
				).build();
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 충전_폼으로_이동() throws Exception {
	    //given
		memberService.join(testMember);
	    //when
		mvc.perform(get("/members/cash")
				.sessionAttr("loginMember", testMember))
	    //then
				.andExpect(status().isOk())
				.andExpect(view().name("members/cash"));
	}

	@Test
	void 충전_테스트() throws Exception {
	    //given
		Long memberId = memberService.join(testMember);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("chargeMoney", String.valueOf(CHARGE_MONEY));

		//when
		mvc.perform(post("/members/cash")
						.sessionAttr("loginMember", testMember)
						.params(params))
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

		assertThat(memberService.findOne(memberId).getCash().left())
				.isEqualTo(CHARGE_MONEY);
	}

	@Test
	void 충전_폼_검증_테스트() {
	    //given
		CashFormDto cashForm = CashFormDto.builder()
				.build();

	    //when
		Set<ConstraintViolation<CashFormDto>> violations = validator.validate(cashForm);

		//then
		assertThat(violations.size()).isEqualTo(1);
	}
}