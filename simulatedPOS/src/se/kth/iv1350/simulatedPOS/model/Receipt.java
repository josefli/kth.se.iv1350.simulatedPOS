package se.kth.iv1350.simulatedPOS.model;

import se.kth.iv1350.simulatedPOS.integration.Printer;

/**
 * This class represents the receipt.
 */

public class Receipt {
	private ReceiptDTO currentReceiptDTO;
	private Printer printer;

	/**
	 * Creates a new receipt an creates an instance of the printer.
	 *
	 * @param saleDTO Sale to make into a receipt.
	 */

	public Receipt(SaleDTO saleDTO){
		this.currentReceiptDTO = new ReceiptDTO(saleDTO);
		this.printer = new Printer();
	}
}
