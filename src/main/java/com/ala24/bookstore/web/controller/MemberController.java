package com.ala24.bookstore.web.controller;

import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.memberdto.MemberFormDto;
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
public class MemberController {

	private final MemberService memberService;

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

		memberService.join(memberForm.toEntity());
		return "redirect:/";
	}
}
