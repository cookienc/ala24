package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.exception.NotEnoughCashException;
import com.ala24.bookstore.repository.CashRepository;
import com.ala24.bookstore.repository.member.MemberRepository;
import com.ala24.bookstore.web.dtos.memberdto.CashFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.ala24.bookstore.exception.utils.Sentence.NOT_ENOUGH_CASH;
import static com.ala24.bookstore.exception.utils.Sentence.NO_MEMBER;

/**
 * 회원의 돈과 관련된 함수를 가지고 있는 서비스 계층
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CashService {

	private final MemberRepository memberRepository;
	private final CashRepository cashRepository;

	/**
	 * 회원의 아이디와 충전시킬 돈을 파라미터를 받아 계좌에 돈을 충전
	 * @param memberId 충전시킬 회원의 아이디
	 * @param cash 충전시킬 돈
	 */
	public void charge(Long memberId, Long cash) {
		Member findMember = findOne(memberId);
		cashRepository.delete(findMember.getCash()); // 기존에 있는 객체를 지우기 위함
		findMember.charge(cash);
	}

	/**
	 * 회원의 아이디와 쓸 돈을 파라미터로 받아 계좌에서 돈을 지불
	 * @param memberId 돈을 빼올 회원의 아이디
	 * @param cash 지불할 돈
	 */
	public void pay(Long memberId, Long cash) {
		Long account = findOne(memberId).account();
		if (account < cash) {
			throw new NotEnoughCashException(NOT_ENOUGH_CASH.toString());
		}
		charge(memberId, -cash);
	}

	/**
	 * 회원 아이디를 파라미터로 받아 회원을 반환
	 * @param memberId
	 * @return
	 */
	private Member findOne(Long memberId) {
		return memberRepository.findById(memberId).stream()
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException(NO_MEMBER.toString()));
	}

	/**
	 * Member를 회원의 로그인 아이디로 조회해서 CashFormDto로 변환하는 함수
	 * @param loginId 회원의 로그인 아이디
	 * @return CashFormDto
	 */
	public CashFormDto toCashFormDto(Long loginId) {
		Member loginMember = findOne(loginId);
		return CashFormDto.builder()
				.name(loginMember.getName())
				.loginId(loginMember.getLoginId())
				.currentAccount(loginMember.getCash().left())
				.chargeMoney(0L)
				.build();
	}
}
