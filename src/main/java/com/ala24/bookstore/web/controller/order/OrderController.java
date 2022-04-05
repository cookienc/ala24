package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.service.OrderService;
import com.ala24.bookstore.web.dtos.orderdto.ItemOrderFormDto;
import com.ala24.bookstore.web.dtos.orderdto.MemberOrderFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.ala24.bookstore.web.session.SessionName.LOGIN_MEMBER;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

	private final MemberService memberService;
	private final ItemService itemService;
	private final OrderService orderService;

	@GetMapping("/{itemId}")
	public String createForm(@PathVariable("itemId") Long itemId, HttpServletRequest request, Model model) {

		Item findItem = itemService.findOne(itemId);
		Member findMember = (Member) request.getSession(false)
				.getAttribute(LOGIN_MEMBER.getName());

		log.info("OrderController itemID : {}, member.name : {}", itemId, findMember.getName());

		model.addAttribute("itemForm", new ItemOrderFormDto().toDto(findItem));
		model.addAttribute("memberForm", new MemberOrderFormDto().toDto(findMember));

		return "order/orderForm";
	}
}
