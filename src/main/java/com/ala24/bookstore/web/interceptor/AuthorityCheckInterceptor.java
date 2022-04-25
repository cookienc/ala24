package com.ala24.bookstore.web.interceptor;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.ala24.bookstore.web.session.SessionName.LOGIN_MEMBER;

@Slf4j
public class AuthorityCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestURI = request.getRequestURI();

		log.info("권한 체크 인터셉터 시작 : {}", requestURI);

		HttpSession session = request.getSession(false);
		Member member = (Member) session.getAttribute(LOGIN_MEMBER.getName());

		if (hasNotAuthority(member)) {
			log.info("권한 없는 사용자 요청");
			response.sendRedirect("/login?redirectURL=" + requestURI);
			return false;
		}

		return true;
	}

	private boolean hasNotAuthority(Member member) {
		return !MemberStatus.ADMIN.equals(member.getAuthority());
	}
}
