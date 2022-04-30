package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.web.controller.search.member.MemberSearch;
import com.ala24.bookstore.web.controller.search.member.MemberSearchCondition;
import com.ala24.bookstore.repository.member.MemberRepository;
import com.ala24.bookstore.web.dtos.memberdto.MemberListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ala24.bookstore.exception.utils.Sentence.NOT_DUPLICATE_MEMBER;
import static com.ala24.bookstore.exception.utils.Sentence.NO_MEMBER;

/**
 * 회원과 관련된 함수를 가지고 있는 서비스 계층
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	/**
	 * 회원을 저장하는 함수
	 * @param member 저장할 회원
	 * @return 저장한 회원의 아이디
	 */
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member).getId();
	}

	/**
	 * 회원을 한 명 조회
	 * @param id 조회할 회원의 아이디
	 * @return 조회한 회원
	 */
	public Member findOne(Long id) {
		return memberRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(NO_MEMBER.toString()));
	}

	/**
	 * 회원 전체 조회
	 * @return 회원 전체를 리스트로 만들어서 반환
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	/**
	 * 회원 검색 조건을 이용하여 회원을 페이징 해서 조회, 조회한 회원들을 MemberListDto로 변환하여 반환
	 * @param condition 회원 검색 조건
	 * @param pageable 페이징 조건
	 * @return 페이징된 MemberListDto
	 */
	public Page<MemberListDto> findAllWithCash(MemberSearch condition, Pageable pageable) {

		if (condition == null) {
			condition.setCondition(MemberSearchCondition.NORMAL);
		}

		return memberRepository.searchPage(condition, pageable)
				.map(MemberListDto::new);
	}

	/**
	 * 회원 삭제 함수
	 * @param savedId 삭제할 회원의 아이디
	 */
	public void delete(Long savedId) {
		Member findMember = findOne(savedId);
		memberRepository.delete(findMember);
	}


	/**
	 * 회원이 존재하는지 확인하는 함수
	 * @param member 확인할 회원
	 */
	private void validateDuplicateMember(Member member) {
		Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());
		if (findMember.isPresent()) {
			throw new IllegalStateException(NOT_DUPLICATE_MEMBER.toString());
		}
	}
}
