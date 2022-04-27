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

/**
 * 회원등록을 하는 컨트롤러
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberPostController {

	private final MemberService memberService;

	/**
	 * 회원등록 화면으로 이동하는 함수
	 * @param model 화면에 보여줄 모델
	 * @return 회원등록폼의 view
	 */
	@GetMapping("/post")
	public String postForm(Model model) {
		model.addAttribute("memberForm", new MemberFormDto());
		return "members/postMemberForm";
	}

	/**
	 * 회원 등록 화면으로 이동하는 함수
	 * @param memberForm 등록할 회원의 정보
	 * @param result 오류를 담을 파라미터
	 * @return 오류가 없으면 홈의 view 경로를 아니면 회원 등록 폼의 view 경로로 이동
	 */
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
