package com.ala24.bookstore.web.controller.item;

import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.web.dtos.itemdto.ItemListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.ala24.bookstore.web.pages.DefaultPageButtonSize.DEFAULT_PAGE_BUTTON_RANGE;
import static com.ala24.bookstore.web.session.SessionAttributeName.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemListController {

	private final ItemService itemService;

//	@GetMapping("/list")
//	public String list(Model model) {
//		List<Item> items = itemService.findAll();
//		model.addAttribute("items", new ItemListDto().toDto(items));
//		return "items/list";
//	}

	@GetMapping("/list")
	public String list(Pageable pageable, @SessionAttribute(name = LOGIN_MEMBER) Member loginMember, Model model) {
		Page<ItemListDto> items = itemService.findAll(pageable);
		model.addAttribute("items", items);
		model.addAttribute("maxPage", DEFAULT_PAGE_BUTTON_RANGE.getPageNum());
		model.addAttribute("loginMember", loginMember);
		return "items/list";
	}
}
