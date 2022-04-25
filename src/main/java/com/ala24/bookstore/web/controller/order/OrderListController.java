package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.domain.orders.condition.OrderSearch;
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

import static com.ala24.bookstore.web.pages.DefaultPageButtonSize.DEFAULT_PAGE_BUTTON_RANGE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderListController {

	private final OrderService orderService;

	@GetMapping("/list")
	public String list(@ModelAttribute("condition") OrderSearch condition, Pageable pageable, Model model) {
		Page<OrderListDto> orders = orderService.findAllFetch(condition, pageable);
		model.addAttribute("orders", orders);
		model.addAttribute("maxPage", DEFAULT_PAGE_BUTTON_RANGE.getPageNum());
		return "orders/list";
	}
}
