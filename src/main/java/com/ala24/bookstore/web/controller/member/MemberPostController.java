package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.dtos.memberdto.MemberFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberPostController {

	private final MemberService memberService;

	/**
	 * 회원 등록
	 */
	@GetMapping("/post")
	public String postForm(Model model) {
		model.addAttribute("memberForm", new MemberFormDto());
		return "members/postMemberForm";
	}

	@PostMapping("/post")
	public String save(@Valid @ModelAttribute("memberForm") MemberFormDto memberForm, BindingResult result) {
		if (result.hasErrors()) {
			log.info("MemberForm 검증 오류 errors={}", result);
			return "members/postMemberForm";
		}

		try {
			memberService.join(memberForm.toEntity());
		} catch (IllegalStateException e) {
			log.info("중복된 회원으로 회원 가입 error={}", result);
			result.rejectValue("loginId", "duplicate", "해당 아이디는 이미 사용중입니다.");
			return "members/postMemberForm";
		}

		return "redirect:/";
	}
}
