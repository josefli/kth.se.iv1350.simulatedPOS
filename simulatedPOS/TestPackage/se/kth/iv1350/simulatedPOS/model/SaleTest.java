package se.kth.iv1350.simulatedPOS.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.simulatedPOS.integration.Inventory;
import se.kth.iv1350.simulatedPOS.integration.ItemDTO;
import se.kth.iv1350.simulatedPOS.integration.RegistryCreator;
import static org.junit.jupiter.api.Assertions.*;

class SaleTest {
	RegistryCreator regCreator = new RegistryCreator();
	Sale sale = new Sale(this.regCreator.getAccounting());
	Inventory inventory = new Inventory();
	private ItemDTO milkDTO = new ItemDTO("00001", "Milk", 10, 5);
	private ItemDTO juiceItemDTO = new ItemDTO("00006", "Juice", 12, 7);
	private ItemDTO shampooItemDTO = new ItemDTO("0007", "Shampoo", 25, 12);

	@BeforeEach
	public void setUp(){
		this.inventory = new Inventory();
	}

	@Test
	void testAddSoldItemEmptyAtStart(){
		boolean acctResult = this.sale.soldItems.isEmpty();
		boolean expResult = true;
		assertEquals(acctResult, expResult, "Sold items are added when sale is initiated.");
	}

	@Test
	void testAddSoldItem() {
		this.sale.addSoldItem(this.shampooItemDTO);
		boolean acctResult = this.sale.soldItems.containsKey(this.shampooItemDTO);
		boolean expResult = true;
		int expNumberOfItemsAdded = 1;
		int acctNumberOfItemsAdded = this.sale.numberOfItemsUsedToCheckConnectionException;

		assertEquals(acctResult, expResult, "Item was added but was not found in sold items.");
		assertEquals(expNumberOfItemsAdded, acctNumberOfItemsAdded,
				  "The numbers of items sold do not equal the expected number.");
	}

	@Test
	void testAddSoldItemMultipleItems() {
		this.sale.addSoldItem(this.shampooItemDTO);
		this.sale.addSoldItem(this.milkDTO);
		boolean acctResult = this.sale.soldItems.containsKey(this.shampooItemDTO) &&
				  this.sale.soldItems.containsKey(this.milkDTO);
		boolean expResult = true;
		int expNumberOfItemsAdded = 2;
		int acctNumberOfItemsAdded = this.sale.numberOfItemsUsedToCheckConnectionException;

		assertEquals(acctResult, expResult, "Items were added but were not found in sold items.");
		assertEquals(expNumberOfItemsAdded, acctNumberOfItemsAdded,
				  "The numbers of items sold do not equal the expected number.");
	}

	@Test
	void testAddSoldItemRunningTotal(){
		this.sale.addSoldItem(this.shampooItemDTO);
		double acctResult = this.sale.getSaleDTO().runningTotal;
		double expResult = this.shampooItemDTO.price;
		assertEquals(acctResult, expResult, "Running total was not updated correctly.");
	}

	@Test
	void testAddSoldItemTax(){
		this.sale.addSoldItem(this.shampooItemDTO);
		double acctResult = this.sale.getSaleDTO().totalTaxOfPurchase;
		double expResult = this.shampooItemDTO.tax;
		assertEquals(acctResult, expResult, "Total VAT was not updated correctly.");
	}

	@Test
	void testAddMultipleSoldItemsRunningTotal(){
		this.sale.addSoldItem(this.shampooItemDTO);
		this.sale.addSoldItem(this.milkDTO);
		double acctResult = this.sale.getSaleDTO().runningTotal;
		double expResult = this.shampooItemDTO.price + this.milkDTO.price;
		assertEquals(acctResult, expResult, "Running total was not updated correctly (2 items).");
	}

	@Test
	void testAddMultipleSoldItemsNumbOfItems(){
		this.sale.addSoldItem(this.shampooItemDTO);
		this.sale.addSoldItem(this.milkDTO);
		double acctResult = this.sale.getSaleDTO().numberOfItems;
		double expResult = 2;
		assertEquals(acctResult, expResult, "Number of items was not updated correctly (2 items).");
	}

	@Test
	void testAddSoldItemIncreasingQuantityByScanNumOfItems(){
		int expNumberOfItemsAdded = 6;
		for(int i = 0; i < expNumberOfItemsAdded; i++){
			this.sale.addSoldItem(this.shampooItemDTO);
		}
		int acctNumberOfItemsAdded = this.sale.numberOfItemsUsedToCheckConnectionException;
		int acctNumberOfSpecificItemAdded = this.sale.soldItems.get(this.shampooItemDTO);

		assertEquals(expNumberOfItemsAdded, acctNumberOfItemsAdded,
				  "The number of items sold do not equal the expected number.");
		assertEquals(expNumberOfItemsAdded, acctNumberOfSpecificItemAdded,
				  "The numbers of items sold do not equal the expected number of certain item.");
	}

	@Test
	void testAddSoldItemIncreasingQuantityByScanCheckRunningTotal(){
		for(int i = 0; i < 4; i++){
			this.sale.addSoldItem(this.shampooItemDTO);
		}
		double expResult = this.sale.numberOfItemsUsedToCheckConnectionException;
		int actResult = this.sale.soldItems.get(this.shampooItemDTO);

		assertEquals(expResult, actResult, "The numbers of items sold do not equal the expected number.");
	}

	@Test
	void addSoldItemIncreasingQuantityByScanNotDirectlyAfterEachOther(){
		this.sale.addSoldItem(this.shampooItemDTO);
		this.sale.addSoldItem(this.juiceItemDTO);
		this.sale.addSoldItem(this.shampooItemDTO);

		int acctNumberOfItemsAdded = this.sale.numberOfItemsUsedToCheckConnectionException;
		int expNumberOfItemsAdded = 3;
		int acctNumberOfSpecificItemAdded = this.sale.soldItems.get(this.shampooItemDTO);
		int expNumberOfSpecificItemAdded = 2;

		assertEquals(expNumberOfItemsAdded, acctNumberOfItemsAdded,
				  "The numbers of items sold do not equal the expected number.");
		assertEquals(expNumberOfSpecificItemAdded, acctNumberOfSpecificItemAdded,
				  "The numbers of items sold do not equal the expected number of certain item.");
	}

	@Test
	void addSoldItemIncreasingQuantityByScanTax(){
		this.sale.addSoldItem(this.shampooItemDTO);
		this.sale.addSoldItem(this.juiceItemDTO);
		this.sale.addSoldItem(this.shampooItemDTO);

		double actResult = this.sale.getSaleDTO().totalTaxOfPurchase;
		double expResult = Math.round(100.0 * (this.shampooItemDTO.price * 2 * this.shampooItemDTO.tax +
				  this.juiceItemDTO.price * this.juiceItemDTO.tax) /
				  (2 * this.shampooItemDTO.price + this.juiceItemDTO.price)) / 100.0;

		assertEquals(expResult, actResult,
				  "The VAT do not equal the expected VAT.");
	}

	@Test
	void testRegisterPaymentGetChange() {

		double amountPaid = 100;
		this.sale.addSoldItem(this.juiceItemDTO);
		this.sale.addSoldItem(this.shampooItemDTO);
		this.sale.registerPayment(amountPaid);

		double expResult = 100 - (this.juiceItemDTO.price + this.shampooItemDTO.price);
		double acctResult = this.sale.paymentDTO.change;

		assertEquals(expResult, acctResult, "RunningTotal is not updated correctly when payment is made.");
	}

	@Test
	void testRegisterPaymentZeroChange() {

		this.sale.addSoldItem(this.juiceItemDTO);
		this.sale.addSoldItem(this.shampooItemDTO);
		double amountPaid = this.sale.runningTotal;
		this.sale.registerPayment(amountPaid);

		double expResult = 0;
		double acctResult = this.sale.paymentDTO.change;

		assertEquals(expResult, acctResult, "Change is returned when payment is exact.");
	}
}

