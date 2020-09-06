package com.equalexperts.shoppingcart.business;

import java.math.BigDecimal;

import com.equalexperts.shoppingcart.constants.Constants;
import com.equalexperts.shoppingcart.data.Product;
import com.equalexperts.shoppingcart.utils.CollectionUtils;

/**
 * Class responsible for total price calculation.
 */
public class TotalCostService extends PriceCalculator  {

	/**
	 * Calculate total price based on product.
	 *  
	 * @param product.
	 * @return
	 */
	public BigDecimal getTotalPriceBasedOnProduct(Product product) {
		if (CollectionUtils.isNotNullOrEmpty(products)) {
			BigDecimal val = products.stream()
					.filter(p -> p.equals(product))
					.map(Product::getPrice)					
					.reduce((a, b) -> a.add(b))
					.orElse(new BigDecimal(0))
					.setScale(Constants.SCALE, Constants.ROUNDING_MODE);
			return val;
		}
		return new BigDecimal(0);
	}

	/**
	 * Calculate total price for all items which are present in cart.
	 * 
	 * @return.
	 */
	public BigDecimal getTotalPrice() {	
		
		checkForCharges();
		
		BigDecimal chargesValue = new BigDecimal(charges.values().stream().mapToDouble(BigDecimal::longValue).sum());		
		if (CollectionUtils.isNotNullOrEmpty(products)) {
			return products.stream()
					.map(Product::getPrice)
					.reduce((a, b) -> a.add(b)).get()					
					.add(chargesValue)
					.setScale(Constants.SCALE, Constants.ROUNDING_MODE);
		}
		return new BigDecimal(0);
	}

	/**
	 * Return the total count of cart items.
	 * 
	 * @return.
	 */
	public int getTotalProductsCount() {
		return products.size();
	}		
}
