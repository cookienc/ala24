package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ala24.bookstore.exception.utils.Sentence.NOT_DUPLICATE_MEMBER;
import static com.ala24.bookstore.exception.utils.Sentence.NO_MEMBER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	/**
	 * 회원가입
	 */
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member).getId();
	}

	/**
	 * 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	/**
	 * 회원 삭제
	 */
	public void delete(Long savedId) {
		Member findMember = findOne(savedId);
		memberRepository.delete(findMember);
	}

	public Member findOne(Long id) {
		return memberRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(NO_MEMBER.toString()));
	}


	private void validateDuplicateMember(Member member) {
		Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());
		if (findMember.isPresent()) {
			throw new IllegalStateException(NOT_DUPLICATE_MEMBER.toString());
		}
	}
}
