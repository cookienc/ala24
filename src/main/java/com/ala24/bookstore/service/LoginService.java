package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.ala24.bookstore.exception.utils.Sentence.DO_NOT_MATCH_PW;
import static com.ala24.bookstore.exception.utils.Sentence.NO_MEMBER;

/**
 * 로그인과 관련된 함수를 가지고 있는 서비스 계층
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
	private final MemberRepository memberRepository;

	/**
	 * 로그인 기능을 하는 함수
	 * @param loginId 로그인을 할 아이디
	 * @param password loginId의 비밀번호
	 * @return 로그인된 회원을 반환
	 */
	public Member login(String loginId, String password) {
		Member findMember = memberRepository.findByLoginId(loginId)
				.orElseThrow(() -> new NoSuchElementException(NO_MEMBER.toString()));

		if (!password.equals(findMember.getPassword())) {
			throw new IllegalArgumentException(DO_NOT_MATCH_PW.toString());
		}

		return findMember;
	}
}
