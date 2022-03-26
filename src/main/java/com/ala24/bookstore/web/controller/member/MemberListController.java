package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.dtos.memberdto.MemberListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", new MemberListDto().toDto(members));
		return "members/memberList";
	}
}
