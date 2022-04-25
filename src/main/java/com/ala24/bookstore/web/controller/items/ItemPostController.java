package com.ala24.bookstore.web.controller.items;

import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.web.dtos.itemdto.ItemFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
	public String save(@Valid @ModelAttribute("itemForm") ItemFormDto itemForm, BindingResult result,
					   RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			log.info("ItemForm 검증 오류 : {}", result);
			return "items/postItemForm";
		}

		itemService.saveItem(itemForm.toEntity());
		return "redirect:/items/list";
	}
}
