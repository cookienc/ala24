package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.service.CashService;
import com.ala24.bookstore.web.dtos.memberdto.CashFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.ala24.bookstore.web.session.SessionAttributeName.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/cash")
public class MemberCashController {

	private final CashService cashService;

	@GetMapping
	public String chargeForm(@SessionAttribute(name = LOGIN_MEMBER) Member loginMember, Model model) {

		CashFormDto cashForm = cashService.toCashFormDto(loginMember.getId());
		model.addAttribute("cashForm", cashForm);
		return "members/cash";
	}

	@PostMapping
	public String charge(@SessionAttribute(name = LOGIN_MEMBER) Member loginMember, CashFormDto cashForm) {

		cashService.charge(loginMember.getId(), cashForm.getChargeMoney());
		return "redirect:/";
	}
}
