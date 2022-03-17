package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.exception.NotEnoughCashException;
import com.ala24.bookstore.repository.CashRepository;
import com.ala24.bookstore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.ala24.bookstore.exception.utils.Sentence.NOT_ENOUGH_CASH;
import static com.ala24.bookstore.exception.utils.Sentence.NO_MEMBER;

@Service
@RequiredArgsConstructor
@Transactional
public class CashService {

	private final MemberRepository memberRepository;
	private final CashRepository cashRepository;

	public void charge(Long memberId, Long cash) {
		Member findMember = findOne(memberId);
		cashRepository.delete(findMember.getCash()); // 기존에 있는 객체를 지우기 위함
		findMember.charge(cash);
	}

	public void pay(Long memberId, Long cash) {
		Long account = findOne(memberId).account();
		if (account < cash) {
			throw new NotEnoughCashException(NOT_ENOUGH_CASH.toString());
		}
		charge(memberId, -cash);
	}

	private Member findOne(Long memberId) {
		return memberRepository.findById(memberId).stream()
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException(NO_MEMBER.toString()));
	}
}
