package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.service.OrderService;
import com.ala24.bookstore.web.dtos.orderdto.OrderListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderListController {

	private final OrderService orderService;

	@GetMapping("/list")
	public String list(Model model) {
		List<OrderListDto> orders = orderService.findAllAsList();
		model.addAttribute("orders", orders);
		return "order/list";
	}
}
