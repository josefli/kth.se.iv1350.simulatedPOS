package se.kth.iv1350.simulatedPOS.startup;

import se.kth.iv1350.simulatedPOS.controller.Controller;
import se.kth.iv1350.simulatedPOS.integration.RegistryCreator;
import se.kth.iv1350.simulatedPOS.view.View;


/**
 * The class that handles the main method of the program and the start up sequence.
 */

public class Main {
	public static void main(String[] args) {

		RegistryCreator registryCreator = new RegistryCreator();
		Controller contr = new Controller(registryCreator);
		View view = new View(contr);

		view.hardCodedSale();
	}

}
