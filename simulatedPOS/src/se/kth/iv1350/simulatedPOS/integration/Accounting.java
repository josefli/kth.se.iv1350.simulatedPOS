package se.kth.iv1350.simulatedPOS.integration;

import se.kth.iv1350.simulatedPOS.model.SaleDTO;

/**
 * Represents the external accounting system.
 */

public class Accounting {

	public boolean saleLogged = false;
	/**
	 * Creates an instance of the accounting system used as a reference.
	 */

	public Accounting(){

	}

	/**
	 * Takes the current sale and logs it in the accounting system. An empty method.
	 *
	 * @param currentSale sale to log
	 */

	public void logSale(SaleDTO currentSale) {

		// Logs the sale in the accounting system
		this.saleLogged = true;
	}
}