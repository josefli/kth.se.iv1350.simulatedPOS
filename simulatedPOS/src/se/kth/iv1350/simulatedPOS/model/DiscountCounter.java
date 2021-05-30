package se.kth.iv1350.simulatedPOS.model;

import se.kth.iv1350.simulatedPOS.integration.DiscountDTO;

/**
 * Represents the discount calculator. Contains the discount business logic.
 */

public class DiscountCounter {

	/**
	 * Creates a new discount counter used as reference. An empty method.
	 */
	public DiscountCounter(){

	}

	/**
	 * Calculates the discount for the provided sale based on store and customer ID. An empty method.
	 *
	 * @param currentSale Sale on which to apply the discounts.
	 * @param customerDiscount The customer specific discounts to apply.
	 * @param storeDiscountDTO The store specific discounts to apply.
	 */
	public void calculateDiscount(Sale currentSale, DiscountDTO customerDiscount, DiscountDTO storeDiscountDTO){

		// Performs discount business logic and updates currentSale

	}
}
