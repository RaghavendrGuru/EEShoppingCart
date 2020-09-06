package com.equalexperts.shoppingcart.enums;

import java.math.BigDecimal;

/**
 * ENUM to hold different type of tax.
 */
public enum TaxType {

	SALE_TAX("SALETAX", "12.5");

	private String code;
	private BigDecimal percentage;

	TaxType(String code, String percentage) {
		this.code = code;
		this.percentage = new BigDecimal(percentage);
	}

	public String getCode() {
		return code;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}
}
