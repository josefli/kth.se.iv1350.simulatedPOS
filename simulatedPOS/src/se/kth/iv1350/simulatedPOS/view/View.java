package se.kth.iv1350.simulatedPOS.view;

import se.kth.iv1350.simulatedPOS.controller.Controller;

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
		double amountCustomerPays = 50;
		String itemID = "00001";
		int quantityToAdd = 3;

		System.out.println("Creates a test database.");
		this.controller.beginNewSale();
		System.out.println("Starts new sale.");
		String itemName = this.controller.scanItem(itemID).itemDescription;
		System.out.println("Item scanned: " + itemName);
		System.out.println("Current running total: " + this.controller.getRunningTotal().runningTotal + " SEK");
		System.out.println("Current total tax: " + this.controller.getRunningTotal().tax + "%");
		this.controller.enterQuantity(quantityToAdd);
		System.out.println(quantityToAdd + " " + itemName + " was added.");
		System.out.println("Current running total: "  + this.controller.getRunningTotal().runningTotal + " SEK");
		System.out.println("Current total tax: " + this.controller.getRunningTotal().tax + "%");
		System.out.println("End sale.");
		System.out.println("Final running total: " + this.controller.endSale().runningTotal + " SEK");
		System.out.println("Total tax of sale: " + this.controller.getRunningTotal().tax + "%");
		System.out.println("Customer pays " + amountCustomerPays + " SEK and receives "
				  + this.controller.amountPaid(amountCustomerPays) + " SEK in change.");
	}
}
