package com.ala24.bookstore.web.controller.login;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.service.LoginService;
import com.ala24.bookstore.web.controller.loginDto.LoginFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.NoSuchElementException;

import static com.ala24.bookstore.exception.utils.Sentence.DO_NOT_MATCH_PW;
import static com.ala24.bookstore.exception.utils.Sentence.NO_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@GetMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginFormDto loginForm) {
		return "login/loginForm";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginForm") LoginFormDto loginForm, BindingResult result) {
		if (result.hasErrors()) {
			log.info("login 검증 오류 errors={}", result);
			return "login/loginForm";
		}

		try {
			Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
		} catch (NoSuchElementException e) {
			result.rejectValue("loginId", "missMatch", NO_MEMBER.toString());
		} catch (IllegalArgumentException e) {
			result.rejectValue("password", "missMatch", DO_NOT_MATCH_PW.toString());
		}

		if (result.hasErrors()) {
			log.info("loginId/password 오류 errors={}", result);
			return "login/loginForm";
		}

		//로그인 성공

		return "redirect:/";
	}

}
