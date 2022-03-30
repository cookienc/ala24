package com.ala24.bookstore.web.controller.login;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.service.LoginService;
import com.ala24.bookstore.web.dtos.logindto.LoginFormDto;
import com.ala24.bookstore.web.session.SessionName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
	public String login(@Valid @ModelAttribute("loginForm") LoginFormDto loginForm, BindingResult result,
						HttpServletRequest request) {
		if (result.hasErrors()) {
			log.info("login 검증 오류 errors={}", result);
			return "login/loginForm";
		}

		Member loginMember = null;
		try {
			loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
		} catch (NoSuchElementException e) {
			result.rejectValue("loginId", "mismatch", NO_MEMBER.toString());
		} catch (IllegalArgumentException e) {
			result.rejectValue("password", "mismatch", DO_NOT_MATCH_PW.toString());
		}

		if (result.hasErrors()) {
			log.info("loginId/password 오류 errors={}", result);
			return "login/loginForm";
		}

		//로그인 성공

		HttpSession session = request.getSession();
		session.setAttribute(SessionName.LOGIN_MEMBER.getName(), loginMember);

		return "redirect:/";
	}

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
}
