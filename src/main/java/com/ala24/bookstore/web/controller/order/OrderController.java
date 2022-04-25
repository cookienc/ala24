package com.ala24.bookstore.web.controller.order;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.exception.NotEnoughCashException;
import com.ala24.bookstore.exception.NotEnoughItemException;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.service.OrderService;
import com.ala24.bookstore.web.dtos.orderdto.OrderFormDto;
import com.ala24.bookstore.web.dtos.orderdto.OrderServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;

import static com.ala24.bookstore.exception.utils.Sentence.NOT_ENOUGH_CASH;
import static com.ala24.bookstore.exception.utils.Sentence.NOT_ENOUGH_ITEM;
import static com.ala24.bookstore.web.session.SessionName.LOGIN_MEMBER;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	public static final String NO_ID = "해당 아이디를 가진 멤버나 아이템이 없습니다.";
	public static final String LEFT_MONEY = "잔액 : ";
	public static final String LEFT_ITEM = "현재 재고 : ";
	private final ItemService itemService;
	private final OrderService orderService;
	private final MemberService memberService;

	@GetMapping("/{itemId}")
	public String createForm(@PathVariable("itemId") Long itemId, HttpServletRequest request, Model model) {

		Item findItem = itemService.findOne(itemId);
		Member findMember = (Member) request.getSession(false)
				.getAttribute(LOGIN_MEMBER.getName());

		model.addAttribute("orderForm", new OrderFormDto().toDto(findItem, findMember));

		return "orders/orderForm";
	}

	@PostMapping
	public String order(@Valid @ModelAttribute("orderForm") OrderFormDto orderForm, BindingResult result) {

		if (result.hasErrors()) {
			log.info("order 검증 오류 result = {}", result);
			return "orders/orderForm";
		}

		OrderServiceDto serviceDto = new OrderServiceDto().toDto(orderForm);

		try {

			orderService.order(serviceDto.getMemberId(), serviceDto.getItemId(), serviceDto.getItemQuantity());

		} catch (NotEnoughCashException e) {
			log.info("잔액이 부족합니다. error = {}", result);

			Long left = memberService.findOne(serviceDto.getMemberId())
					.getCash().left();

			result.reject("notEnoughMoney", NOT_ENOUGH_CASH.toString() +
					LEFT_MONEY + left);
			return "orders/orderForm";

		} catch (NoSuchElementException e) {
			log.info("아이템이나 멤버가 없습니다. error = {}", result);
			result.reject("noId", NO_ID);
			return "orders/orderForm";

		} catch (NotEnoughItemException e) {
			log.info("재고가 부족합니다. error = {}", result);
			result.reject("notEnoughItem", NOT_ENOUGH_ITEM +
					LEFT_ITEM + orderForm.getStockQuantity());
			return "orders/orderForm";
		}

		return "redirect:/";
	}
}
