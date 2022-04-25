package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.orders.condition.OrderSearch;
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
import static com.ala24.bookstore.web.session.SessionAttributeName.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderListController {

	private final OrderService orderService;

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
