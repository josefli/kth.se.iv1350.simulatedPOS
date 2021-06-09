package se.kth.iv1350.simulatedPOS.view;

import se.kth.iv1350.simulatedPOS.controller.Controller;
import se.kth.iv1350.simulatedPOS.integration.ItemDTO;

/**
 * Represents the view.
 */

public class View {

	private Controller controller;

	/**
	 * Creates a view.
	 * @param contr Controller that the view communicates with.
	 */

	public View(Controller contr) {
		this.controller = contr;
	}

	/**
	 * Runs the steps of a sale with hard coded parameters and method calls.
	 */

	public void hardCodedSale() {
		double amountCustomerPays = 100;
		this.controller.beginNewSale();

		presentNewScannedItem("00001");
		presentNewScannedItem("00002");
		presentNewScannedItem("00001");
		presentNewScannedItem("00003");

		System.out.println("End sale.");
		System.out.println("Final running total: " + this.controller.endSale().runningTotal + " SEK");
		System.out.println("Total tax of sale: " + this.controller.getRunningTotal().tax + "%\n\n");
		this.controller.makePayment(amountCustomerPays);
	}

	private void presentNewTotal(){
		System.out.println("Current running total:\t   " + this.controller.getRunningTotal().runningTotal + " SEK");
		System.out.println("Current total tax:\t\t\t " + this.controller.getRunningTotal().tax + "%\n");
	}

	private void presentNewScannedItem(String itemID) {
		ItemDTO item = this.controller.scanItem(itemID);
		System.out.println("Item scanned: " + item.itemDescription + "\t\t" + item.price + "SEK");
		presentNewTotal();

	}
}
