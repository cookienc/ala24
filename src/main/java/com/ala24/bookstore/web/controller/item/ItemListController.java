package com.ala24.bookstore.web.controller.item;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.web.dtos.itemdto.ItemListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemListController {

	private final ItemService itemService;

	@GetMapping("/list")
	public String list(Model model) {
		List<Item> items = itemService.findAll();
		model.addAttribute("items", new ItemListDto().toDto(items));
		return "items/list";
	}
}
