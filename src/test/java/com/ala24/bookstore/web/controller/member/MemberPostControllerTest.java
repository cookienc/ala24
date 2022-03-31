package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.dtos.memberdto.MemberFormDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.Validator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberPostControllerTest {

	@Autowired
	private MemberPostController memberPostController;

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

	private MemberFormDto noLoginId;
	private MemberFormDto noPassword;
	private MemberFormDto noName;
	private MemberFormDto normal;

	@BeforeEach
	public void setUp() {
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

}