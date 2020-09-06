package com.equalexperts.shoppingcart.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.equalexperts.shoppingcart.data.Product;
import com.equalexperts.shoppingcart.model.Tax;

/**
 * Handle price calculation activity.
 */
public class PriceCalculator extends Cart {

	public Map<Product, BigDecimal> charges = new HashMap<Product, BigDecimal>();

	/**
	 * Generic API for tax calculation.
	 */
	public void checkForCharges() {
		if (charges.size() == 0) {
			Map<Long, Tax> productTaxMap = products
					.stream()
					.filter((a) -> a.getTax() != null)
					.collect(Collectors.toMap(Product::getId, Product::getTax , (id1, id2) -> { return id1; }, LinkedHashMap::new));
			
			//Process Tax.
			for (Map.Entry<Long, Tax> entry : productTaxMap.entrySet()) {
				Tax saleTax = (Tax)entry.getValue();
				saleTax.applyTax(
						getTotalProductsCountById(entry.getKey()), 
						getProductById(entry.getKey()), 
						charges);
			}
		}
	}
}
