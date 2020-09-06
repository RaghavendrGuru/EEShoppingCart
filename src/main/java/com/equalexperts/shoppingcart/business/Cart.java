package com.equalexperts.shoppingcart.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.equalexperts.shoppingcart.data.Product;
import com.equalexperts.shoppingcart.enums.ProductType;

/**
 * Shopping cart class which will add items to cart. 
 */
public class Cart {

	public List<Product> products;

	/**
	 * Initialize product list.
	 */
	public Cart() {
		products = new ArrayList<Product>();
	}
	
	/**
	 * Add item to shopping cart.
	 * 
	 * @param product.
	 * @param quantity.
	 */
	public void addItem(Product product, int quantity) {
		if (product == null || quantity < 0) {
			return;
		}
		if (ProductType.getProductByName(product.getName()) != null)
			IntStream.range(0, quantity).forEach(a -> products.add(product));
	}
	
	/**
	 * Return the total count of cart items based product.
	 * 
	 * @param product.
	 * @return size.
	 */
	public int getTotalProductsCountById(Long productId) {
		return products.stream().filter(p -> p.getId() == productId).collect(Collectors.toList()).size();

	}

	/**
	 * Return the total count of cart items based product.
	 * 
	 * @param product.
	 * @return size.
	 */
	public int getTotalProductsCountByName(Product product) {
		return products.stream().filter(p -> p.equals(product)).collect(Collectors.toList()).size();
	}

	/**
	 * Return the total count of cart items based product.
	 * 
	 * @param product.
	 * @return size.
	 */
	public Product getProductById(Long productId) {
		return products.stream().filter(x -> productId == x.getId()).findAny().orElse(null);

	}
}
