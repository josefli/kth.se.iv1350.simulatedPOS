package se.kth.iv1350.simulatedPOS.integration;

import java.util.HashMap;

/**
 * DTO class that represents discounts for a customer or a store.
 */

public class DiscountDTO {

	final String identificationOfCustomerOrStore;
	private HashMap <String, Double> discountCollection;

	/**
	 * Creates a new discountDTO.
	 *
	 * @param identification The store ID or customerID
	 * @param discountDescription Description of the discount.
	 * @param discountAmount How much of a discount there is.
	 */

	public DiscountDTO(String identification, String discountDescription, double discountAmount){
		this.discountCollection = new HashMap<>();
		this.identificationOfCustomerOrStore = identification;
		this.discountCollection.put(discountDescription, discountAmount);
	}
}
