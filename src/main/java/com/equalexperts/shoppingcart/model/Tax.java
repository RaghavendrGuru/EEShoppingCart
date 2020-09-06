package com.equalexperts.shoppingcart.model;

import java.math.BigDecimal;
import java.util.Map;

import com.equalexperts.shoppingcart.data.Product;

/**
 * Apply Tax for products.
 */
public interface Tax {

	void applyTax(int totalProductCount, Product product, Map<Product, BigDecimal> charges);
	
}
