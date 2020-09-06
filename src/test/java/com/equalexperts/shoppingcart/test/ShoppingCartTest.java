package com.equalexperts.shoppingcart.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.equalexperts.shoppingcart.business.TotalCostService;
import com.equalexperts.shoppingcart.data.Product;
import com.equalexperts.shoppingcart.enums.ProductType;
import com.equalexperts.shoppingcart.model.SaleTaxImpl;

/**
 * Test cases for all shopping cart test cases.
 */
public class ShoppingCartTest {

	TotalCostService shoppingCart;
	Product soap;
	Product deo;
	Product dummy;

	/**
	 * Initialize ShoppingCart & Product classes.
	 */
	@Before
	public void setup() {
		//Initialize  cart.
		shoppingCart = new TotalCostService();
		
		//Initialize  all products.
		ProductType.init();
		
		//Product-1 of type Soap.
		soap = ProductType.getProductByName("Dove Soap");
				
		//Product-2 of type Deo.
		deo = ProductType.getProductByName("Axe Deo");
						
		//Dummy product for test case.
		dummy = ProductType.getProductByName("Dummy Product Name");		
	}
  
	/**
	 * Step 1: Add products to the shopping cart.
	 */
	@Test
	public void addProductsToShoppingCartTest() {
		/*
		 * When -> The user adds 5 Dove Soaps to the shopping cart.
		 */
		shoppingCart.addItem(soap, 5);
		shoppingCart.addItem(deo, 2);

		/*
		 * Then -> 
		 * The shopping cart should contain 5 Dove Soaps each with a unit price of 39.99. 
		 * And the shopping cart’s total price should equal 199.95.
		 */
		assertEquals(shoppingCart.getTotalProductsCountByName(soap), 5);
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(soap), new BigDecimal("199.95"));	
		
		assertEquals(shoppingCart.getTotalProductsCountByName(deo), 2);
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(deo), new BigDecimal("199.98"));	
		
		assertEquals(shoppingCart.getTotalProductsCount(), 7);
		assertEquals(shoppingCart.getTotalPrice(), new BigDecimal("399.93"));
	}
	
	/**
	 * Test case to add negative items to cart.
	 */
	@Test
	public void addNegativeQuantityToCartTest() {
		shoppingCart.addItem(soap, -10);
		assertEquals(shoppingCart.getTotalProductsCountByName(soap), 0);
	}
	
	/**
	 * Test case to add invalid product to cart.
	 */
	@Test
	public void addInvalidProductToCartTest() {
		
		//add dummy/invalid product.
		shoppingCart.addItem(dummy, -10);
		
		assertEquals(shoppingCart.getTotalProductsCountByName(dummy), 0);
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(dummy), new BigDecimal(0));
		assertEquals(shoppingCart.getTotalPrice(), new BigDecimal(0));	
	}
		
	/**
	 * Get total price value when cart is empty.
	 */
	@Test
	public void getTotalPriceWhenShoppingCartIsEmptyTest() {
		
		//Do not add any item to cart.			
		
		assertEquals(shoppingCart.getTotalPrice(), new BigDecimal(0));		
	}
	
	/**
	 * Get total price by product name when particular item (soap) is not added to cart.
	 */
	@Test
	public void getTotalPriceByProductNameWhenItemNotPresetInCartTest() {
		
		//Do not add soap item to cart.			
		
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(soap), new BigDecimal(0));		
	}
	
	/**
	 * Step 2: Add additional products of the same type to the shopping cart.
	 */
	@Test
	public void updateProductsToShoppingCartTest() {
		/*
		 * When -> 
		 * The user adds 5 Dove Soaps to the shopping cart.
		 * And then adds another 3 Dove Soaps to the shopping cart.
		 */
		shoppingCart.addItem(soap, 5);
		shoppingCart.addItem(soap, 3);

		/*
		 * Then -> 
		 * The shopping cart should contain 8 Dove Soaps each with a unit price of 39.99 
		 * And the shopping cart’s total price should equal 319.92
		 */
		assertEquals(shoppingCart.getTotalProductsCountByName(soap), 8);		
		assertEquals(shoppingCart.getTotalPrice(), new BigDecimal("319.92"));		
	}
	
	/**
	 * Test case to check price and product component after adding NULL object to cart.
	 */
	@Test
	public void checkPriceProductComponentAfterAddingNullObjectToCartTest() {
		
		//Add null item to cart.
		shoppingCart.addItem(null, 5);
		
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(soap), new BigDecimal(0));
		assertEquals(shoppingCart.getTotalPrice(), new BigDecimal(0));
		assertEquals(shoppingCart.getTotalProductsCountByName(soap), 0);
		assertEquals(shoppingCart.getTotalProductsCount(), 0);
	}
	
	/**
	 * Test case to check price and product component after adding partial NULL object to cart. 
	 */
	@Test
	public void checkPriceProductComponentAfterAddingNullAndValidItemToCartTest() {
		
		//Add null and valid items.
		shoppingCart.addItem(null, 5);
		shoppingCart.addItem(soap, 1);
		shoppingCart.addItem(deo, 1);
		
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(soap), new BigDecimal("39.99"));
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(deo), new BigDecimal("99.99"));
		assertEquals(shoppingCart.getTotalPrice(), new BigDecimal("139.98"));
		assertEquals(shoppingCart.getTotalProductsCountByName(soap), 1);
		assertEquals(shoppingCart.getTotalProductsCountByName(deo), 1);
		assertEquals(shoppingCart.getTotalProductsCount(), 2);
	}	
	
	// ----------------------------------------------------- Tax Component -----------------------------------------------//
	/**
	 * Step 3: Calculate the tax rate of the shopping cart with multiple items.
	 */
	@Test
	public void calculateTaxRateForMultipleItemsInCartTest() {

		// Populate products with saleTax
		Product soap = ProductType.getProductByName("Dove Soap");
		soap.setTax(new SaleTaxImpl());

		Product deo = ProductType.getProductByName("Axe Deo");
		deo.setTax(new SaleTaxImpl());

		/*
		 * When -> The user adds 2 Dove Soaps to the shopping cart. 
		 * And then adds 2 Axe Deos to the shopping cart.
		 */
		shoppingCart.addItem(soap, 2);
		shoppingCart.addItem(deo, 2);

		/*
		 * Then -> The shopping cart should contain 2 Dove Soaps each with a unit price
		 * of 39.99 And the shopping cart should contain 2 Axe Deos each with a unit
		 * price of 99.99 And the total sales tax amount for the shopping cart should
		 * equal 35.00 And the shopping cart’s total price should equal 314.96
		 */
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(soap), new BigDecimal("79.98"));
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(deo), new BigDecimal("199.98"));
		assertEquals(shoppingCart.getTotalPrice(), new BigDecimal("314.96"));
	}	
	
	/**
	 * Test when tax component set as NULL.
	 */
	@Test
	public void calculateTaxComponentWhenValueSetToNullTest() {

		Product soap = ProductType.getProductByName("Dove Soap");
		soap.setTax(null);

		shoppingCart.addItem(soap, 2);
		
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(soap), new BigDecimal("79.98"));
		assertEquals(shoppingCart.getTotalPrice(), new BigDecimal("79.98"));
	}
	
	/**
	 * check total price based on product when particular product is not added to cart.
	 */
	@Test
	public void checkTotalPriceBasedOnProductWhenProductNotInCartTest() {

		Product soap = ProductType.getProductByName("Dove Soap");
		Product deo = ProductType.getProductByName("Axe Deo");
		
		shoppingCart.addItem(soap, 2);
		
		assertEquals(shoppingCart.getTotalPriceBasedOnProduct(deo), new BigDecimal("0.00"));
		assertEquals(shoppingCart.getTotalPrice(), new BigDecimal("79.98"));
	}
}
