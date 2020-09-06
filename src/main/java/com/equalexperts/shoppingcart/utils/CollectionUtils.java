package com.equalexperts.shoppingcart.utils;

import java.util.Collection;

/**
 * Utility class for shopping cart.
 */
public class CollectionUtils {

	/**
	 * Utility to check whether collection is isNotNull Or Empty.
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotNullOrEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}
}
