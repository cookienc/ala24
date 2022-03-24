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

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@GetMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginFormDto loginForm) {
		System.out.println("==============================GetMapping");
		log.info("GetMapping loginForm id : {}, pw : {}", loginForm.getLoginId(), loginForm.getPassword());
		return "login/loginForm";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginForm") LoginFormDto loginForm, BindingResult result) {
		System.out.println("==============================PostMapping");
		log.info("PostMapping loginForm id : {}, pw : {}", loginForm.getLoginId(), loginForm.getPassword());

		if (result.hasErrors()) {
			log.info("login 검증 오류 errors={}", result);
			return "login/loginForm";
		}

		//TODO validator 만들어서 아이다와 비밀번호 틀렸을 경우 controller 단에서 해결하기

		Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

		//로그인 성공

		return "redirect:/";
	}

}
