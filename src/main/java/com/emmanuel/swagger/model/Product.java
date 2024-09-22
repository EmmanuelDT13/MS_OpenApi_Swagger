package com.emmanuel.swagger.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="This is the product payload.")
public class Product {
	
	@Schema(description="This is the unique id for each product.", example="7")
	private Integer id;
	
	@Schema(description="This is the name of the product.", example="Iphone 16 Plus.")
	private String name;
	
	@Schema(description="This is the product price.", example="2000")
	private Integer price;
	
	public Product(Integer id, String name, Integer price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
