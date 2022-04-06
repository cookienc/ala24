package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.service.OrderService;
import com.ala24.bookstore.web.dtos.orderdto.ItemOrderFormDto;
import com.ala24.bookstore.web.dtos.orderdto.MemberOrderFormDto;
import com.ala24.bookstore.web.dtos.orderdto.OrderFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.ala24.bookstore.web.session.SessionName.LOGIN_MEMBER;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

	private final ItemService itemService;
	private final OrderService orderService;

	@GetMapping("/{itemId}")
	public String createForm(@PathVariable("itemId") Long itemId, HttpServletRequest request, Model model) {

		Item findItem = itemService.findOne(itemId);
		Member findMember = (Member) request.getSession(false)
				.getAttribute(LOGIN_MEMBER.getName());

		model.addAttribute("itemForm", new ItemOrderFormDto().toDto(findItem));
		model.addAttribute("memberForm", new MemberOrderFormDto().toDto(findMember));
		model.addAttribute("orderForm", new OrderFormDto());

		return "order/orderForm";
	}

	@PostMapping
	public String order(@ModelAttribute("orderForm") OrderFormDto orderForm) {

		log.info("orderController orderForm.getItemId = {}", orderForm.getItemId());
		log.info("orderController orderForm.getItemName = {}", orderForm.getItemName());
		log.info("orderController orderForm.getPrice = {}", orderForm.getPrice());
		log.info("orderController orderForm.getQuantity = {}", orderForm.getQuantity());
		log.info("orderController orderForm.getMemberId = {}", orderForm.getMemberId());
		log.info("orderController orderForm.getMemberName = {}", orderForm.getMemberName());
		log.info("orderController orderForm.getCity = {}", orderForm.getAddress().getCity());
		log.info("orderController orderForm.getSpecificAddress = {}", orderForm.getAddress().getSpecificAddress());
		log.info("orderController orderForm.get = {}", orderForm.getAddress().getZipcode());

		orderService.order(orderForm.getMemberId(), orderForm.getItemId(), orderForm.getQuantity());

		return "order/list";
	}
}
