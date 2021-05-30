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
	private ItemDTO juiceItemDTO = new ItemDTO("00006", "Juice", 12, 7);
	private ItemDTO shampooItemDTO = new ItemDTO("0007", "Shampoo", 25, 12);

	@BeforeEach
	public void setUp(){
		this.inventory = new Inventory();
	}

	@Test
	void addSoldItemEmptyAtStart(){
		boolean acctResult = this.sale.soldItems.isEmpty();
		boolean expResult = true;
		assertEquals(acctResult, expResult, "Sold items are added when sale is initiated.");
	}

	@Test
	void addSoldItem() {
		this.sale.addSoldItem(this.shampooItemDTO);
		boolean acctResult = this.sale.soldItems.containsKey(this.shampooItemDTO);
		boolean expResult = true;
		int expNumberOfItemsAdded = 1;
		int acctNumberOfItemsAdded = this.sale.numberOfItems;

		assertEquals(acctResult, expResult, "Item was added but was not found in inventory.");
		assertEquals(expNumberOfItemsAdded, acctNumberOfItemsAdded, "The numbers of items sold do not equal the expected number.");
	}

	@Test
	void addSoldItemIncreasingQuantityByScan(){
		int expNumberOfItemsAdded = 6;
		for(int i = 0; i < expNumberOfItemsAdded; i++){
			this.sale.addSoldItem(this.shampooItemDTO);
		}
		int acctNumberOfItemsAdded = this.sale.numberOfItems;
		int acctNumberOfSpecificItemAdded = this.sale.soldItems.get(this.shampooItemDTO);

		assertEquals(expNumberOfItemsAdded, acctNumberOfItemsAdded, "The numbers of items sold do not equal the expected number.");
		assertEquals(expNumberOfItemsAdded, acctNumberOfSpecificItemAdded, "The numbers of items sold do not equal the expected number of certain item.");
	}

	@Test
	void addSoldItemIncreasingQuantityByScanNotDirectlyAfterEachOther(){
		this.sale.addSoldItem(this.shampooItemDTO);
		this.sale.addSoldItem(this.juiceItemDTO);
		this.sale.addSoldItem(this.shampooItemDTO);

		int acctNumberOfItemsAdded = this.sale.numberOfItems;
		int expNumberOfItemsAdded = 3;
		int acctNumberOfSpecificItemAdded = this.sale.soldItems.get(this.shampooItemDTO);
		int expNumberOfSpecificItemAdded = 2;

		assertEquals(expNumberOfItemsAdded, acctNumberOfItemsAdded,
				  "The numbers of items sold do not equal the expected number.");
		assertEquals(expNumberOfSpecificItemAdded, acctNumberOfSpecificItemAdded,
				  "The numbers of items sold do not equal the expected number of certain item.");
	}

	@Test
	void registerPaymentPrice() {
		this.sale.addSoldItem(this.juiceItemDTO);
		this.sale.addSoldItem(this.shampooItemDTO);
		this.sale.registerPayment(100);

		double expResult = this.juiceItemDTO.price + this.shampooItemDTO.price;
		double acctResult = this.sale.runningTotal;

		assertEquals(expResult, acctResult, "RunningTotal is not updated correctly when payment is made.");
	}

	@Test
	void registerPaymentGetChange() {
		double amountPaid = 100;
		this.sale.addSoldItem(this.juiceItemDTO);
		this.sale.addSoldItem(this.shampooItemDTO);
		this.sale.registerPayment(amountPaid);

		double expResult = 100 - (this.juiceItemDTO.price + this.shampooItemDTO.price);
		double acctResult = this.sale.paymentDTO.change;

		assertEquals(expResult, acctResult, "RunningTotal is not updated correctly when payment is made.");
	}
}