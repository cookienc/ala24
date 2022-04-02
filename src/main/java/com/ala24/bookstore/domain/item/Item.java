package com.ala24.bookstore.domain.item;

import com.ala24.bookstore.exception.NotEnoughItemException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.ala24.bookstore.exception.utils.Sentence.NOT_ENOUGH_ITEM;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "item_id")
	private Long id;

	private String name;
	private String author;
	private String publisher;
	private int price;
	private int stockQuantity;

	protected Item(Long id, String name, String author, String publisher, int price, int stockQuantity) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public void validateOrder(int orderCount) {
		if (this.stockQuantity < orderCount) {
			throw new NotEnoughItemException(NOT_ENOUGH_ITEM.toString());
		}
	}

	public int removeStock(int count) {
		this.stockQuantity -= count;
		return this.stockQuantity;
	}

	public int addStock(int count) {
		return this.stockQuantity += count;
	}

	public void updateInfo(Item item) {
		this.name = item.getName();
		this.author = item.getAuthor();
		this.publisher = item.getPublisher();
		this.price = item.getPrice();
		this.stockQuantity = item.getStockQuantity();
	}
}
