package se.kth.iv1350.simulatedPOS.model;

import se.kth.iv1350.simulatedPOS.integration.Accounting;
import se.kth.iv1350.simulatedPOS.integration.ItemDTO;
import se.kth.iv1350.simulatedPOS.integration.StoreDTO;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents a sale in the store.
 */

public class Sale {
	LocalTime timeOfSale;
	public double runningTotal;
	double tax;
	public int numberOfItems;
	private ItemDTO lastAddedItem;
	private Accounting accounting;
	PaymentDTO paymentDTO;
	SaleDTO currentSaleDTO;
	RunningTotalDTO currentRunningTotalDTO;
	StoreDTO storeDTO;
	HashMap <ItemDTO, Integer> soldItems;

	/**
	 * Creates a new sale.
	 * @param accounting Reference to accounting.
	 */

	public Sale(Accounting accounting){
		setTimeOfSale();
		this.accounting = accounting;
		this.soldItems = new HashMap<>();
		this.runningTotal = 0;
		this.numberOfItems = 0;
		this.paymentDTO = new PaymentDTO(0, 0);
		this.tax = 0;
		this.currentSaleDTO = new SaleDTO(this);
		this.currentRunningTotalDTO = new RunningTotalDTO(this);
		this.storeDTO = new StoreDTO("My Store", "Cool Street", "1337", "7337", "Funk Town");
	}

	private void setTimeOfSale(){
		this.timeOfSale = LocalTime.now();
	}

	private void newSaleDTO(){
		this.currentSaleDTO = new SaleDTO(this);
	}

	private void newRunningTotalDTO(){
		this.currentRunningTotalDTO = new RunningTotalDTO(this);
	}

	private void updateDTOs(){
		this.newRunningTotalDTO();
		this.newSaleDTO();
	}

	private void calculateAndUpdateAverageTax(){
		double allTaxAmountsAdded = 0;
		Set<ItemDTO> itemKeySet = this.soldItems.keySet();
		for (ItemDTO item : itemKeySet) {
			int quantityOfItem = this.soldItems.get(item);
			double itemTax = item.tax;
			double itemPrice = item.price;
			allTaxAmountsAdded += itemPrice * itemTax * quantityOfItem;
		}
		double averageTax = allTaxAmountsAdded / this.runningTotal;
		this.tax = averageTax;
	}

	private void increaseSoldItem(ItemDTO itemToIncrease){
		this.soldItems.put(itemToIncrease, this.soldItems.get(itemToIncrease) + 1);
		updateSaleParameters();
	}

	private void updateSaleParameters(){
		calculateAndUpdateAverageTax();
		updateDTOs();
	}

	/**
	 * Getter for the running total DTO of the current sale.
	 *
	 * @return The current running total DTO.
	 */

	public RunningTotalDTO getRunningTotalDTO() {
		return this.currentRunningTotalDTO;
	}

	/**
	 * Getter for the current sale DTO.
	 * @return The current sale DTO.
	 */

	public SaleDTO getSaleDTO(){
		return this.currentSaleDTO;
	}

	/**
	 * Adds a sold item to the sale and returns the updated current sale DTO.
	 * @param itemToAdd Item to add to the sale.
	 * @return The updated sale DTO.
	 */

	public SaleDTO addSoldItem(ItemDTO itemToAdd){

		if(this.soldItems.containsKey(itemToAdd)) {
			increaseSoldItem(itemToAdd);
		}
		else{
			this.soldItems.put(itemToAdd, 1);
		}
		this.runningTotal += itemToAdd.price;
		this.numberOfItems += 1;
		this.lastAddedItem = itemToAdd;
		updateSaleParameters();
		return this.getSaleDTO();
	}

	/**
	 * Increases the quantity of the last added object.
	 *
	 * @param quantity How many of the item there should be
	 * @return The updated running total.
	 */

	public RunningTotalDTO increaseQuantity(int quantity){
		soldItems.put(lastAddedItem, soldItems.get(lastAddedItem) + quantity - 1);
		this.runningTotal += lastAddedItem.price * (quantity - 1);
		updateSaleParameters();
		return this.getRunningTotalDTO();
	}

	/**
	 * Registers the sale with accounting.
	 *
	 * @param amount The amount that was paid.
	 */

	public void registerPayment(double amount){
		double change = amount - this.runningTotal;
		this.paymentDTO = new PaymentDTO(amount, change);
		this.accounting.logSale(this.getSaleDTO());
		this.newSaleDTO();
		this.newRunningTotalDTO();
		new Receipt(this.getSaleDTO());
	}
}
