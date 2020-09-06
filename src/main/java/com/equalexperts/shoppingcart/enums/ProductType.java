package com.equalexperts.shoppingcart.enums;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.equalexperts.shoppingcart.data.Product;

/**
 * ENUM to hold product information.
 */
public enum ProductType {
	
	SOAP(1l, "Dove Soap", "39.99"), 
	DEO(2l, "Axe Deo", "99.99"), 
	DUMMY(3l, "Dummy Product Name", "-100.99");

	private Long id;
	private String name;
	private BigDecimal price;
	private static List<Product> products;

	ProductType(Long id, String name, String price) {
		this.id = id;
		this.name = name;
		this.price = new BigDecimal(price);
	}

	/**
	 * Initialize all products.
	 */
	public static void init() {
		products = new ArrayList<Product>();
		for (ProductType enumObj : values()) {
			Product product = new Product();
			product.setId(enumObj.getId());
			product.setName(enumObj.getName());
			product.setPrice(enumObj.getPrice());
			products.add(product);
		}
	}

	/**
	 * Get Product by name.
	 * 
	 * @param name
	 * @return
	 */
	public static Product getProductByName(String name) {
		for (Product product : products) {
			if (product.getName().equalsIgnoreCase(name)) {
				return product;
			}
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public static List<Product> getProducts() {
		return products;
	}
}
