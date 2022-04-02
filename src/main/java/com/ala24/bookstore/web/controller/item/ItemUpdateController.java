package com.ala24.bookstore.web.controller.item;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.web.dtos.itemdto.ItemUpdateFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemUpdateController implements ItemCategoryController{

	private final ItemService itemService;

	@GetMapping("/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Item findItem = itemService.findOne(itemId);

		ItemUpdateFormDto updateForm = new ItemUpdateFormDto();
		model.addAttribute("updateForm", updateForm.setInfo(findItem));
		return "items/updateForm";
	}

	@PostMapping("/{itemId}/edit")
	public String update(@PathVariable("itemId") Long itemId, @Valid @ModelAttribute("updateForm") ItemUpdateFormDto updateForm,
						 BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			log.info("ItemUpdateForm 검증 오류 : {}", result);
			return "items/updateForm";
		}

		itemService.updateItem(itemId, updateForm.toEntity());
		return "redirect:/items/list";
	}
}
