package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderCancelController {

	private final OrderService orderService;

	@PostMapping("/{orderId}/cancel")
	public String cancelOrder(@PathVariable("orderId") Long orderId) {
		log.info("OrderCancelController -> cancelOrder : orderId {}", orderId);

		orderService.cancel(orderId);

		return "redirect:/";
	}
}
