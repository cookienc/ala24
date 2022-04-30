package com.ala24.bookstore.web.controller.item;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.item.condition.ItemSearch;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.web.dtos.itemdto.ItemListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.ala24.bookstore.web.pages.DefaultPageButtonSize.DEFAULT_PAGE_BUTTON_RANGE;
import static com.ala24.bookstore.web.session.SessionName.LOGIN_MEMBER;

/**
 * 아이템을 조회하는 컨트롤러
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemListController {

	private final ItemService itemService;

	/**
	 * 아이템을 조회하는 함수
	 * 회원의 권한이 ADMIN인 경우만 재고 목록을 보이게 함
	 * @param condition 검색 조건
	 * @param loginMember 로그인된 회원
	 * @param pageable 페이징 조건
	 * @param model 모델
	 * @return 상품 리스트를 보여주는 view 경로
	 */
	@GetMapping("/list")
	public String search(@ModelAttribute(value = "condition") ItemSearch condition,
						 @SessionAttribute(name = LOGIN_MEMBER, required = false) Member loginMember, Pageable pageable, Model model) {

		Page<ItemListDto> items = itemService.search(condition, pageable);
		model.addAttribute("items", items);
		model.addAttribute("loginMember", loginMember);
		model.addAttribute("maxPage", DEFAULT_PAGE_BUTTON_RANGE.getPageNum());
		return "items/list";
	}
}
