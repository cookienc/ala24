package com.ala24.bookstore.web.dtos.itemdto;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.Novel;
import com.ala24.bookstore.domain.item.Poem;
import com.ala24.bookstore.domain.item.SelfDevelopment;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.ala24.bookstore.web.dtos.itemdto.ItemCategory.NOVEL;
import static com.ala24.bookstore.web.dtos.itemdto.ItemCategory.POEM;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemFormDto {

	@NotBlank
	private String name;

	@NotBlank
	private String author;

	@NotBlank
	private String publisher;

	@NotNull
	private ItemCategory category;

	@NotNull
	@Range(min = 1000, max = 100_000)
	private int price;

	@NotNull
	@Range(min = 10, max = 9999)
	private int stockQuantity;

	public Item toEntity() {
		if (isIt(POEM)) {
			return getPoem();
		}

		if (isIt(NOVEL)) {
			return getNovel();
		}

		return getSelfDevelopment();
	}

	private Novel getNovel() {
		return Novel.builder()
				.name(this.name)
				.author(this.author)
				.publisher(this.publisher)
				.price(this.price)
				.stockQuantity(this.stockQuantity)
				.build();
	}

	private Poem getPoem() {
		return Poem.builder()
				.name(this.name)
				.author(this.author)
				.publisher(this.publisher)
				.price(this.price)
				.stockQuantity(this.stockQuantity)
				.build();
	}

	private SelfDevelopment getSelfDevelopment() {
		return SelfDevelopment.builder()
				.name(this.name)
				.author(this.author)
				.publisher(this.publisher)
				.price(this.price)
				.stockQuantity(this.stockQuantity)
				.build();
	}

	private boolean isIt(Enum<ItemCategory> category) {
		return this.category.equals(category);
	}
}
