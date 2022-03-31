package com.ala24.bookstore.web.dtos.itemdto;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.Novel;
import com.ala24.bookstore.domain.item.Poem;
import com.ala24.bookstore.domain.item.SelfDevelopment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ala24.bookstore.web.dtos.itemdto.ItemCategory.NOVEL;
import static com.ala24.bookstore.web.dtos.itemdto.ItemCategory.POEM;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemFormDto {

	private String name;

	private String author;


	private String publisher;

	private ItemCategory category;

	private int price;

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
