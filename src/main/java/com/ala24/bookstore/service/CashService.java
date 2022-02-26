package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.repository.CashRepository;
import com.ala24.bookstore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashService {

	private final MemberRepository memberRepository;
	private final CashRepository cashRepository;

	@Transactional
	public void charge(Long memberId, Long cash) {
		Member findMember = memberRepository.findById(memberId).stream()
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("해당 멤버는 없는 멤버입니다."));
		cashRepository.delete(findMember.getCash()); // 기존에 있는 객체를 지우기 위함
		findMember.charge(cash);
	}


}
