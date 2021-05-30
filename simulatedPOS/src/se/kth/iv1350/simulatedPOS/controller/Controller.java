package se.kth.iv1350.simulatedPOS.controller;

import se.kth.iv1350.simulatedPOS.integration.CustomerDTO;
import se.kth.iv1350.simulatedPOS.integration.DiscountDTO;
import se.kth.iv1350.simulatedPOS.integration.ItemDTO;
import se.kth.iv1350.simulatedPOS.integration.RegistryCreator;
import se.kth.iv1350.simulatedPOS.model.*;

/**
 * The controller class, connecting the view with the model and integration layers.
 */

public class Controller {

	public RegistryCreator registryCreator;
	private DiscountCounter discountCounter;
	private Sale currentSale;
	private CashRegister cashRegister;

	/**
	 * Creates an instance of the controller class, with required references to manage interaction between layers.
	 *
	 * @param registryCreator The registry creator to which the controller makes calls regarding registries.
	 */

	public Controller(RegistryCreator registryCreator){

		this.discountCounter = new DiscountCounter();
		this.registryCreator = registryCreator;
		this.cashRegister = new CashRegister();
	}

	private void updateInventory(){
		this.registryCreator.getInventory().updateInventory(this.currentSale);
	}

	/**
	 * Returns the current sale.
	 * @return Current sale.
	 */

	public Sale getSale(){
		return this.currentSale;
	}

	/**
	 * Begins a new sale by creating a new sale object.
	 */

	public void beginNewSale(){
		this.currentSale = new Sale(this.registryCreator.getAccounting());
	}

	/**
	 * Finalizes the sale and returns the total for the purchase.
	 * @return The current running total DTO.
	 */
	public RunningTotalDTO endSale(){
		return getRunningTotal();
	}

	/**
	 * Searches for a provided item identifier and returns the corresponding itemDTO.
	 *
	 * @param itemIdentifier The identifier being scanned from the item.
	 * @return ItemDTO for the scanned item.
	 */

	public ItemDTO scanItem(String itemIdentifier) {
		ItemDTO itemDTO = this.registryCreator.getItemDTO(itemIdentifier);
		this.currentSale.addSoldItem(itemDTO);
		return itemDTO;
	}

	/**
	 * A getter for the current running total DTO.
	 *
	 * @return RunningTotalDTO of the current sale.
	 */

	public RunningTotalDTO getRunningTotal(){
		return this.currentSale.getRunningTotalDTO();
	}

	/**
	 * Increases the quantity for the last item added and returns the updated running total.
	 *
	 * @param quantity how many of the item that should be registered.
	 * @return The updated running total.
	 */

	public RunningTotalDTO enterQuantity(int quantity){
		RunningTotalDTO newRunningTotal = this.currentSale.increaseQuantity(quantity);
		return newRunningTotal;
	}

	/**
	 * Returns the discounted total based on the provided customerID.
	 *
	 * @param customerID The identification linked to discounts.
	 * @return discounted running total.
	 */
	public RunningTotalDTO requestDiscount(String customerID) {

		CustomerDTO customerDTO = this.registryCreator.getCustomerRegistry().findCustomer(customerID);
		SaleDTO saleDTO = this.currentSale.getSaleDTO();
		DiscountDTO customerDiscountDTO = this.registryCreator.getDiscountRegistry().findCustomerDiscount(customerDTO);
		DiscountDTO storeDiscountDTO = this.registryCreator.getDiscountRegistry().findStoreDiscount(saleDTO);

		this.discountCounter.calculateDiscount(this.currentSale, customerDiscountDTO, storeDiscountDTO);

		RunningTotalDTO discountedTotalDTO = this.currentSale.getRunningTotalDTO();
		return discountedTotalDTO;
	}

	/**
	 * Enters the amount paid to the cash register and returns the change.
	 *
	 * @param amount the amount to add to the cash register
	 * @return the amount of change for the purchase
	 */
	public double amountPaid(double amount){
		double change =  this.cashRegister.addPayment(amount, this.currentSale.getSaleDTO());
		this.currentSale.registerPayment(amount);
		updateInventory();
		return change;
	}
}