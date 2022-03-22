package com.ala24.bookstore.web.controller;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.memberdto.MemberFormDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MemberControllerTest {

	@Autowired
	private MemberController memberController;

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

	private MemberFormDto noLoginId;
	private MemberFormDto noPassword;
	private MemberFormDto noName;
	private MemberFormDto normal;

	@BeforeEach
	public void setMvc() {
		mvc = MockMvcBuilders.standaloneSetup(memberController).build();
	}

	@BeforeEach
	public void setUp() {
		noLoginId = new MemberFormDto("", "12345",
				"test", "Seoul", "GangNam", 12345);
		noPassword = new MemberFormDto("test", "",
				"test", "Seoul", "GangNam", 12345);
		noName = new MemberFormDto("test", "12345",
				"", "Seoul", "GangNam", 12345);
		normal = new MemberFormDto("test", "12345",
				"test", "Seoul", "GangNam", 12345);
	}

	@Test
	void 회원가입_폼_출력_확인() throws Exception {
		this.mvc.perform(get("/members/post"))
				.andExpect(status().isOk())
				.andExpect(view().name("members/postMemberForm"));
	}

	@Test
	void 회원가입_성공() throws Exception {
		this.mvc.perform(post("/members/post")
						.flashAttr("memberForm", normal))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	void 회원가입_실패시_원래_화면으로_돌아감() throws Exception {
		this.mvc.perform(post("/members/post")
						.flashAttr("memberForm", noName))
				.andExpect(status().isOk())
				.andExpect(view().name("members/postMemberForm"));
	}
	
	@Test
	void 회원가입_제한_사항_테스트() {
	    //given
		//when
		Set<ConstraintViolation<MemberFormDto>> violationsOfNoLoginId = validator.validate(noLoginId);
		Set<ConstraintViolation<MemberFormDto>> violationsOfNoPassword = validator.validate(noPassword);
		Set<ConstraintViolation<MemberFormDto>> violationsOfNoName = validator.validate(noName);
		Set<ConstraintViolation<MemberFormDto>> violationsOfNormal = validator.validate(normal);

		//then
		assertThat(violationsOfNoLoginId.size()).isEqualTo(1);
		assertThat(violationsOfNoPassword.size()).isEqualTo(2);//비밀번호는 2개의 제약사항이 존재
		assertThat(violationsOfNoName.size()).isEqualTo(1);
		assertThat(violationsOfNormal.size()).isEqualTo(0);
	}
}