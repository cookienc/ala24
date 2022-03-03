package com.ala24.bookstore.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
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

	protected Item(String name, String author, String publisher, int price, int stockQuantity) {
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}
}
