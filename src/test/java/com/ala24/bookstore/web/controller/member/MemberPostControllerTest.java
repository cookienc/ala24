package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.dtos.memberdto.MemberFormDto;
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
class MemberPostControllerTest {

	private static LinkedMultiValueMap<String, String> params;

	private static Stream<Arguments> sampleData() {
		MemberFormDto memberForm = new MemberFormDto("test", "12345", "test", "Seoul",
				"GangNam", 12345);
		MemberFormDto noId = new MemberFormDto("", "12345", "test", "Seoul",
				"GangNam", 12345);
		MemberFormDto noPassword = new MemberFormDto("test", "", "test", "Seoul",
				"GangNam", 12345);
		MemberFormDto outOfMaxRangePassword = new MemberFormDto("test", "01234567891234567", "test", "Seoul",
				"GangNam", 12345);
		MemberFormDto outOfMinRangePassword = new MemberFormDto("test", "111", "test", "Seoul",
				"GangNam", 12345);
		MemberFormDto noName = new MemberFormDto("test", "12345", "", "Seoul",
				"GangNam", 12345);

		return Stream.of(
				Arguments.of(memberForm, 0),
				Arguments.of(noId, 1),
				Arguments.of(noPassword, 2),
				Arguments.of(outOfMaxRangePassword, 1),
				Arguments.of(outOfMinRangePassword, 1),
				Arguments.of(noName, 1)
		);
	}

	@Autowired
	private MockMvc mvc;
	@Autowired
	private Validator validator;

	@Autowired
	private MemberService memberService;

	@Autowired
	private DataBaseCleanup dataBaseCleanup;

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@BeforeEach
	public void setUp() {
		params = new LinkedMultiValueMap<>();
		params.add("loginId", "test");
		params.add("password", "test");
		params.add("name", "test");
		params.add("city", "test");
		params.add("specificAddress", "test");
		params.add("zipcode", "12345");
	}

	@Test
	void 회원가입_폼_출력_확인() throws Exception {
		//given
		//when
		this.mvc.perform(get("/members/post"))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("members/postMemberForm"));
	}

	@Test
	void 회원가입_성공() throws Exception {
		//given
		//when
		this.mvc.perform(post("/members/post")
				.params(params))
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	void 회원가입_실패시_원래_화면으로_돌아감() throws Exception {
		//given
		params.remove("name");
		params.add("name", "");
		//when
		this.mvc.perform(post("/members/post")
				.params(params))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("members/postMemberForm"));
	}

	@ParameterizedTest
	@MethodSource("sampleData")
	void 검증_테스트(MemberFormDto memberForm, int count) {
		//given
		//when
		Set<ConstraintViolation<MemberFormDto>> violations = validator.validate(memberForm);
		//then
		assertThat(violations.size()).isEqualTo(count);
	}
}