package com.ala24.bookstore.web.controller.item;

import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.web.dtos.itemdto.ItemFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemPostController implements ItemCategoryController{

	private final ItemService itemService;

	@GetMapping("/post")
	public String postForm(Model model) {
		log.info("ItemPostController(Get) 요청");
		model.addAttribute("itemForm", new ItemFormDto());
		return "items/postItemForm";
	}

	@PostMapping("/post")
	public String save(@ModelAttribute("itemForm") ItemFormDto itemForm) {

		itemService.saveItem(itemForm.toEntity());
		return "redirect:/items/list";
	}
}
