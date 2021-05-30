package se.kth.iv1350.simulatedPOS.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.simulatedPOS.integration.ItemDTO;
import se.kth.iv1350.simulatedPOS.integration.RegistryCreator;
import se.kth.iv1350.simulatedPOS.model.RunningTotalDTO;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
	private Controller contr = new Controller(new RegistryCreator());

	@BeforeEach
	void setUp() {
		this.contr.beginNewSale();
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testBeginNewSaleNotNull() {
		assertNotNull(this.contr.getSale());
	}

	@Test
	void testEndSale() {
		/* this.contr.currentSale.addSoldItem(new ItemDTO("0001", "Milk", 12, 5));
		RunningTotalDTO expResult = this.contr.currentSale.getRunningTotalDTO();
		RunningTotalDTO acctResult = this.contr.endSale();
		assertEquals(expResult, acctResult, "RunningTotalDTO of sale is not same as end sale running total."); */
	}

	@Test
	void testScanItem() {
		ItemDTO item = this.contr.scanItem("00001");
		boolean expResult = true;
		boolean actResult = this.contr.getSale().getSaleDTO().soldItems.containsKey(item);
		assertEquals(expResult, actResult, "Scanned item was not added to sale and returned correctly.");
	}

	@Test
	void testGetRunningTotal() {
		ItemDTO firstAddedItem = this.contr.scanItem("00001");
		ItemDTO secondAddedItem = this.contr.scanItem("00002");
		double expTotal = firstAddedItem.price + secondAddedItem.price;
		double expTax = (firstAddedItem.tax * firstAddedItem.price
				  + secondAddedItem.tax * secondAddedItem.price) / expTotal;

		RunningTotalDTO runningTotalDTO = this.contr.getRunningTotal();
		double actTotal = runningTotalDTO.runningTotal;
		double actTax = runningTotalDTO.tax;

		assertEquals(expTotal, actTotal, "The expected total and actual total differs.");
		assertEquals(expTax, actTax, "The expected tax and actual tax differs.");
	}

	@Test
	void TestEnterQuantity() {
		String itemID = "00001";
		int quantity = 4;
		ItemDTO item = this.contr.scanItem(itemID);
		this.contr.enterQuantity(quantity);
		int expResult = quantity;
		int acctResult = this.contr.getSale().getSaleDTO().soldItems.get(item);

		assertEquals(expResult, acctResult, "The quantity of the item was not added correctly.");
	}

	@Test
	void amountPaid() {
		ItemDTO item = this.contr.scanItem("00001");
		double amount = 50;
		this.contr.endSale();
		double actChange = this.contr.amountPaid(amount);
		double expChange = amount - item.price;

		double actPaidAmount = this.contr.getSale().getSaleDTO().payment.getAmountPaid();

		assertEquals(expChange, actChange, "The expected change differs from the actual registered change.");
		assertEquals(amount, actPaidAmount, "The paid amount registered does not match the actual amount paid.");
	}
}