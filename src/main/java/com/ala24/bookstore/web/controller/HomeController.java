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

/**
 * 홈 화면과 관련된 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

	/**
	 * 홈 화면을 보여주는 컨트롤러
	 * @param loginMember 로그인이 되었는지의 유무와 회원의 권한을 확인하기 위한 컨트롤러
	 * @param model 로그인 화면의 뷰
	 * @return 로그인이 되어있고 권한이 ADMIN이면 관리자 화면, 로그인만 되어있으면 로그인 화면, 나머지는 그냥 홈의 view 경로로 반환
	 */
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
