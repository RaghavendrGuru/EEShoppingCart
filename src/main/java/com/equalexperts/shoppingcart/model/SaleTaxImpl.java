package com.equalexperts.shoppingcart.model;

import java.math.BigDecimal;
import java.util.Map;

import com.equalexperts.shoppingcart.constants.Constants;
import com.equalexperts.shoppingcart.data.Product;
import com.equalexperts.shoppingcart.enums.TaxType;

/**
 * Implementation class for sale tax.
 */
public class SaleTaxImpl implements Tax {

	private static final BigDecimal DIVISOR = new BigDecimal(100.00);

	@Override
	public void applyTax(int totalProductCount, Product product, Map<Product, BigDecimal> charges) {
		
		BigDecimal totalAmountPayableForAllProduct = product.getPrice().multiply(new BigDecimal(totalProductCount));
		
		BigDecimal calculatedSaleTaxAmount = ((totalAmountPayableForAllProduct
				.multiply(TaxType.SALE_TAX.getPercentage())).divide(DIVISOR));
		
		charges.put(product, calculatedSaleTaxAmount.setScale(Constants.SCALE, Constants.ROUNDING_MODE));
	}
}
