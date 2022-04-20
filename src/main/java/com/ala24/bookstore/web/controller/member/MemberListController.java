package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.dtos.memberdto.MemberListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ala24.bookstore.web.pages.DefaultPageButtonSize.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberListController {

	private final MemberService memberService;

	/**
	 * 회원 목록 조회
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, Model model) {
		Page<MemberListDto> members = memberService.findAllWithCash(pageable);
		model.addAttribute("members", members);
		model.addAttribute("maxPage", DEFAULT_PAGE_BUTTON_RANGE.getPageNum());
		return "members/list";
	}
}
