package com.ala24.bookstore.web.dtos.itemdto;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.Novel;
import com.ala24.bookstore.domain.item.Poem;
import com.ala24.bookstore.domain.item.SelfDevelopment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateFormDto {

	private Long itemId;

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

	public ItemUpdateFormDto setInfo(Item item) {
		this.itemId = item.getId();
		this.name = item.getName();
		this.author = item.getAuthor();
		this.publisher = item.getPublisher();
		this.category = findCategory(item);
		this.price = item.getPrice();
		this.stockQuantity = item.getStockQuantity();

		return this;
	}

	private ItemCategory findCategory(Item item) {
		if (item instanceof Novel) {
			return ItemCategory.NOVEL;
		}

		if (item instanceof Poem) {
			return ItemCategory.POEM;
		}

		return ItemCategory.SELF_DEVELOPMENT;
	}

	public Item toEntity() {
		if (this.category.equals(ItemCategory.NOVEL)) {
			return getNovel();
		}

		if (this.category.equals(ItemCategory.POEM)) {
			return getPoem();
		}

		return getSelfDevelopment();
	}

	private Novel getNovel() {
		return Novel.builder()
				.id(this.itemId)
				.name(this.name)
				.author(this.author)
				.publisher(this.publisher)
				.price(this.price)
				.stockQuantity(this.stockQuantity)
				.build();
	}

	private Poem getPoem() {
		return Poem.builder()
				.id(this.itemId)
				.name(this.name)
				.author(this.author)
				.publisher(this.publisher)
				.price(this.price)
				.stockQuantity(this.stockQuantity)
				.build();
	}

	private SelfDevelopment getSelfDevelopment() {
		return SelfDevelopment.builder()
				.id(this.itemId)
				.name(this.name)
				.author(this.author)
				.publisher(this.publisher)
				.price(this.price)
				.stockQuantity(this.stockQuantity)
				.build();
	}
}
