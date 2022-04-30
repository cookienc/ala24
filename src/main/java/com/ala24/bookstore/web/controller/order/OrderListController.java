package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.web.controller.search.order.OrderSearch;
import com.ala24.bookstore.domain.type.MemberStatus;
import com.ala24.bookstore.service.OrderService;
import com.ala24.bookstore.web.dtos.orderdto.OrderListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.ala24.bookstore.web.pages.DefaultPageButtonSize.DEFAULT_PAGE_BUTTON_RANGE;
import static com.ala24.bookstore.web.session.SessionName.LOGIN_MEMBER;

/**
 * 주문 목록를 보여주는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderListController {

	private final OrderService orderService;

	/**
	 * 주문 리스트를 보여주는 함수
	 * 회원의 권한이 ADMIN인 경우 -> 모든 주문을 볼 수 있음
	 * 나머지 회원은 자신의 주문만 확인 할 수 있음
	 * @param condition 주문 검색 조건
	 * @param pageable 페이징 조건
	 * @param model 화면에 보여줄 모델
	 * @param loginMember 회원의 정보를 가져올 조건
	 * @return 주문 목록 화면의 view 경로
	 */
	@GetMapping("/list")
	public String list(@ModelAttribute("condition") OrderSearch condition, Pageable pageable, Model model,
					   @SessionAttribute(LOGIN_MEMBER) Member loginMember) {
		Page<OrderListDto> orders = null;
		if (hasItAuthority(loginMember)) {
			orders = orderService.findAllFetch(condition, null, pageable);
		} else {
			orders = orderService.findAllFetch(condition, loginMember.getLoginId(), pageable);
		}
		model.addAttribute("orders", orders);
		model.addAttribute("maxPage", DEFAULT_PAGE_BUTTON_RANGE.getPageNum());
		return "orders/list";
	}

	private boolean hasItAuthority(Member loginMember) {
		return loginMember.getAuthority() == MemberStatus.ADMIN;
	}
}
