package com.ala24.bookstore.web.controller.login;

import com.ala24.bookstore.domain.member.Member;
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

/**
 * 로그인을 담당하는 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	/**
	 * 로그인 폼으로 이동하는 함수
	 * @param loginForm 기본 로그인 폼
	 * @return 로그인 화면의 view 경로
	 */
	@GetMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginFormDto loginForm) {
		return "login/loginForm";
	}

	/**
	 * 로그인 기능을 구현하는 컨트롤러
	 * @param loginForm 입력한 로그인 폼
	 * @param result 오류가 들어갈 result
	 * @param request request를 이용해서 session을 가져옴
	 * @return 에러가 발생하면 loginForm, 아니면 loginHome의 view 경로를 반환
	 */
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

	/**
	 * 로그아웃 기능을 수행하는 함수
	 * @param request 로그인된 회원을 조회할 때 사용
	 * @return home화면의 view 경로
	 */
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
}
