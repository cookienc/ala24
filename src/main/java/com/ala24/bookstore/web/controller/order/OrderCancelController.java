package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 주문 취소 기능을 하는 컨트롤러
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderCancelController {

	private final OrderService orderService;

	/**
	 * 주문취소 기능을 하는 함수
	 * @param orderId 취소할 주문의 id
	 * @return 홈화면의 view 경로
	 */
	@PostMapping("/{orderId}/cancel")
	public String cancelOrder(@PathVariable("orderId") Long orderId) {
		log.info("OrderCancelController -> cancelOrder : orderId {}", orderId);

		orderService.cancel(orderId);

		return "redirect:/";
	}
}
