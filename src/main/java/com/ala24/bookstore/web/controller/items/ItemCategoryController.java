package com.ala24.bookstore.web.controller.items;

import com.ala24.bookstore.web.dtos.itemdto.ItemCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public interface ItemCategoryController {

	@ModelAttribute("itemCategories")
	default ItemCategory[] itemCategories() {
		return ItemCategory.values();
	}
}
