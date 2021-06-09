package se.kth.iv1350.simulatedPOS.model;

import se.kth.iv1350.simulatedPOS.integration.ItemDTO;
import se.kth.iv1350.simulatedPOS.integration.StoreDTO;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * DTO class that contains the information of the sale. Instances are immutable.
 */

public final class SaleDTO {
	final LocalDateTime timeOfSale;
	public final double runningTotal;
	public final int numberOfItems;
	public final StoreDTO storeDTO;
	final double totalTaxOfPurchase;
	public final PaymentDTO payment;
	public final HashMap<ItemDTO, Integer> soldItems;

	/**
	 * Creates a sale DTO of the current sale.
	 * @param currentSale Sale to create the DTO from.
	 */

	public SaleDTO(Sale currentSale){
		this.timeOfSale = currentSale.timeOfSale;
		this.runningTotal = currentSale.runningTotal;
		this.numberOfItems = currentSale.numberOfItemsUsedToCheckConnectionException;
		this.storeDTO = currentSale.storeDTO;
		this.totalTaxOfPurchase = Math.round(100.0 * currentSale.tax) / 100.0;
		this.payment = currentSale.paymentDTO;
		this.soldItems = currentSale.soldItems;
	}
}
