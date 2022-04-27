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

/**
 * 상품 등록 컨트롤러
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemPostController implements ItemCategoryController{

	private final ItemService itemService;

	/**
	 * 상품 등록 폼으로 이동하는 함수
	 * @param model 기본 폼
	 * @return 상품 등록 화면의 view 경로
	 */
	@GetMapping("/post")
	public String postForm(Model model) {
		log.info("ItemPostController(Get) 요청");
		model.addAttribute("itemForm", new ItemFormDto());
		return "items/postItemForm";
	}

	/**
	 * 상품 등록하는 함수
	 * @param itemForm 입력된 상품 폼
	 * @param result 오류를 저장할 result
	 * @param redirectAttributes redirect시 uri에 자원을 표시하지 않게 하기 위함
	 * @return 성공하면 상품 리스트의 view 경로, 아니면 상품 등록 화면의 view 경로 반환
	 */
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
