package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.ala24.bookstore.exception.utils.Sentence.DO_NOT_MATCH_PW;
import static com.ala24.bookstore.exception.utils.Sentence.NO_MEMBER;

@Service
@RequiredArgsConstructor
public class LoginService {
	private final MemberRepository memberRepository;

	/**
	 * 로그인 기능
	 */
	@Transactional
	public Member login(String loginId, String password) {
		Member findMember = memberRepository.findByLoginId(loginId)
				.orElseThrow(() -> new NoSuchElementException(NO_MEMBER.toString()));

		if (!password.equals(findMember.getPassword())) {
			throw new IllegalArgumentException(DO_NOT_MATCH_PW.toString());
		}

		return findMember;
	}
}
