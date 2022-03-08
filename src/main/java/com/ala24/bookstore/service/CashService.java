package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.exception.NotEnoughCashException;
import com.ala24.bookstore.repository.CashRepository;
import com.ala24.bookstore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CashService {

	private final MemberRepository memberRepository;
	private final CashRepository cashRepository;

	public void charge(Long memberId, Long cash) {
		Member findMember = findMember(memberId);
		cashRepository.delete(findMember.getCash()); // 기존에 있는 객체를 지우기 위함
		findMember.charge(cash);
	}

	public void pay(Long memberId, Long cash) {
		Long account = findMember(memberId).account();
		if (account < cash) {
			throw new NotEnoughCashException("계좌에 돈이 부족합니다.");
		}
		charge(memberId, -cash);
	}

	private Member findMember(Long memberId) {
		return memberRepository.findById(memberId).stream()
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("해당 멤버는 없는 멤버입니다."));
	}
}
