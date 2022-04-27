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

/**
 * 회원의 돈과 관련된 기능을 수행하는 컨트롤러
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members/cash")
public class MemberCashController {

	private final CashService cashService;

	/**
	 * 계좌 충전 화면으로 이동하는 함수
	 * @param loginMember 기본적인 회원의 정보를 표시하기 위해서 사용
	 * @param model 화면에 보여주는 용도
	 * @return 계좌 충전 화면으로 이동
	 */
	@GetMapping
	public String chargeForm(@SessionAttribute(name = LOGIN_MEMBER) Member loginMember, Model model) {

		CashFormDto cashForm = cashService.toCashFormDto(loginMember.getId());
		model.addAttribute("cashForm", cashForm);
		return "members/cash";
	}

	/**
	 * 계좌에 돈을 충전하는 함수
	 * @param loginMember 돈을 충전할 회원
	 * @param cashForm 얼마만큼 돈을 충전할것인지
	 * @param result 오류를 담는 파라미터
	 * @return 실패하면 충전화면으로, 성공하면 홈으로 이동
	 */
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
