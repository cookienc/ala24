package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.service.CashService;
import com.ala24.bookstore.web.dtos.memberdto.CashFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ala24.bookstore.web.session.SessionAttributeName.LOGIN_MEMBER;

@Controller
@Slf4j
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
	public String charge(@SessionAttribute(name = LOGIN_MEMBER) Member loginMember,
						 @Valid @ModelAttribute("cashForm") CashFormDto cashForm, BindingResult result) {

		if (result.hasErrors()) {
			log.info("CashCharge 검증 오류:{}", result);
			return "members/cash";
		}

		cashService.charge(loginMember.getId(), cashForm.getChargeMoney());
		return "redirect:/";
	}
}
