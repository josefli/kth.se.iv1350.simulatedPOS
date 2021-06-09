package se.kth.iv1350.simulatedPOS.integration;

import se.kth.iv1350.simulatedPOS.model.ReceiptDTO;

/**
 * Represents the external printer.
 */
public class Printer {

	/**
	 * Creates an instance of the printer used as a reference. An empty method.
	 */
	public Printer(){

	}

	/**
	 * Prints the receiptDTO created by receipt. An empty method.
	 *
	 * @param receiptDTO Receipt to be printed.
	 */
	public void printReceipt(ReceiptDTO receiptDTO){
		System.out.println(receiptDTO.fullReceipt);
	}
}
