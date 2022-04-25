package com.ala24.bookstore.web.controller;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.ala24.bookstore.web.session.SessionAttributeName.LOGIN_MEMBER;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping
	public String homeLogin(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member loginMember, Model model) {

		log.info("로그인 확인 : {}", loginMember);

		if (loginMember == null) {
			return "home";
		}

		if (loginMember.getAuthority().equals(MemberStatus.ADMIN)) {
			return "adminHome";
		}

		model.addAttribute("member", loginMember);
		return "loginHome";
	}
}
