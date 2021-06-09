package se.kth.iv1350.simulatedPOS.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.simulatedPOS.integration.ItemDTO;
import se.kth.iv1350.simulatedPOS.integration.RegistryCreator;
import se.kth.iv1350.simulatedPOS.model.RunningTotalDTO;

import java.io.InvalidObjectException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
	private RegistryCreator regCreator = new RegistryCreator();
	private Controller contr = new Controller(regCreator);

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
	void testEndSaleRunningTotal() {
		this.contr.getSale().addSoldItem(new ItemDTO("0001", "Milk", 12, 5));
		RunningTotalDTO expResult = this.contr.getSale().getRunningTotalDTO();
		RunningTotalDTO acctResult = this.contr.endSale();
		assertEquals(expResult, acctResult, "RunningTotalDTO of sale is not same as end sale running total.");
	}

	@Test
	void testScanItemAddedTOSoldItems() {
		ItemDTO item = this.contr.scanItem("00001");
		boolean expResult = true;
		boolean actResult = this.contr.getSale().getSaleDTO().soldItems.containsKey(item);
		assertEquals(expResult, actResult, "Scanned item was not added to sale and returned correctly.");
	}

	@Test
	void testScanMultipleItemsAddedTOSoldItems() {
		ItemDTO firstItem = this.contr.scanItem("00001");
		ItemDTO secondItem = this.contr.scanItem("00001");
		ItemDTO thirdItem = this.contr.scanItem("00001");
		boolean expResult = true;
		boolean actResultFirst = this.contr.getSale().getSaleDTO().soldItems.containsKey(firstItem);
		assertEquals(expResult, actResultFirst, "First Scanned item was not added to sale and " +
				  "returned correctly.");

		boolean actResultSecond = this.contr.getSale().getSaleDTO().soldItems.containsKey(secondItem);
		assertEquals(expResult, actResultSecond, "Second scanned item was not added to sale and " +
				  "returned correctly.");

		boolean actResultThird = this.contr.getSale().getSaleDTO().soldItems.containsKey(thirdItem);
		assertEquals(expResult, actResultThird, "Third scanned item was not added to sale and " +
				  "returned correctly.");

	}

	@Test
	void testScanItemUpdateRunningTotal() {
		String itemID = "00001";

		this.contr.scanItem(itemID);
		double expResult = this.regCreator.getItemDTO(itemID).price;
		double actResult = this.contr.getSale().getSaleDTO().runningTotal;
		assertEquals(expResult, actResult, "Running total was incorrectly updated.");
	}

	@Test
	void testScanMultipleItemsRunningTotalUpdated() {

		ItemDTO firstItem = this.contr.scanItem("00001");
		ItemDTO secondItem = this.contr.scanItem("00001");
		ItemDTO thirdItem = this.contr.scanItem("00001");
		double expResult = firstItem.price + secondItem.price + thirdItem.price;
		double actResult = this.contr.getSale().getSaleDTO().runningTotal;
		assertEquals(expResult, actResult, "Running total was not updated correctly after " +
				  "multiple items were added.");
	}

	@Test
	void testScanItemTwice() {
		this.contr.scanItem("00001");
		ItemDTO item = this.contr.scanItem("00001");
		boolean expResult = true;
		boolean actResult = this.contr.getSale().getSaleDTO().soldItems.containsKey(item);
		assertEquals(expResult, actResult, "Scanned item was not added to sale and returned correctly.");
	}

	@Test
	void testScanItemBetweenTwoItems() {
		this.contr.scanItem("00001");
		ItemDTO item = this.contr.scanItem("00002");
		this.contr.scanItem("00001");
		boolean expResult = true;
		boolean actResult = this.contr.getSale().getSaleDTO().soldItems.containsKey(item);
		assertEquals(expResult, actResult, "Scanned item was not added to sale and returned correctly.");
	}

	@Test
	void testScanItemIncreaseQuantity() {

		this.contr.scanItem("00001");
		ItemDTO item = this.contr.scanItem("00001");
		int expResult = 2;
		int actResult = this.contr.getSale().getSaleDTO().soldItems.get(item);
		assertEquals(expResult, actResult, "Scanned item was not added to sale and returned correctly.");
	}

	@Test
	void testScanItemIncreaseQuantityNotScannedImmediatelyAfter() {

		this.contr.scanItem("00001");
		this.contr.scanItem("00004");
		this.contr.scanItem("00001");
		this.contr.scanItem("00003");
		ItemDTO item = this.contr.scanItem("00001");
		int expResult = 3;
		int actResult = this.contr.getSale().getSaleDTO().soldItems.get(item);
		assertEquals(expResult, actResult, "Item was scanned multiple times but the sold " +
				  "quantity was not increased.");
	}

	@Test
	void testGetRunningTotal() {

		ItemDTO firstAddedItem = this.contr.scanItem("00001");
		ItemDTO secondAddedItem = this.contr.scanItem("00002");
		double expTotal = firstAddedItem.price + secondAddedItem.price;
		double expTax = Math.round(100.0 * (firstAddedItem.tax * firstAddedItem.price
				  + secondAddedItem.tax * secondAddedItem.price) / expTotal) / 100.0;

		RunningTotalDTO runningTotalDTO = this.contr.getRunningTotal();
		double actTotal = runningTotalDTO.runningTotal;
		double actTax = runningTotalDTO.tax;

		assertEquals(expTotal, actTotal, "The expected total and actual total differs.");
		assertEquals(expTax, actTax, "The expected tax and actual tax differs.");
	}

	@Test
	void testMakePayment() {

		ItemDTO item = this.contr.scanItem("00001");
		double amount = 50;
		this.contr.endSale();
		double actChange = this.contr.makePayment(amount);
		double expChange = amount - item.price;

		double actPaidAmount = this.contr.getSale().getSaleDTO().payment.getAmountPaid();

		assertEquals(expChange, actChange, "The expected change differs from the actual registered change.");
		assertEquals(amount, actPaidAmount, "The paid amount registered does not match " +
				  "the actual amount paid.");
	}

	@Test
	void testMakePaymentSameAsRunningTotal() {
		ItemDTO item = this.contr.scanItem("00001");
		double amount = item.price;
		this.contr.endSale();
		double actChange = this.contr.makePayment(amount);
		double expChange = amount - item.price;

		double actPaidAmount = this.contr.getSale().getSaleDTO().payment.getAmountPaid();

		assertEquals(expChange, actChange, "Actual change was not zero.");
		assertEquals(amount, actPaidAmount, "The paid amount registered does not match " +
				  "the actual amount paid.");
	}


	@Test
	void testMakePaymentNoChange() {
		ItemDTO item = this.contr.scanItem("00001");
		double amount = item.price;
		this.contr.endSale();
		double actChange = this.contr.makePayment(amount);
		double expChange = 0;

		double actPaidAmount = this.contr.getSale().getSaleDTO().payment.getAmountPaid();

		assertEquals(expChange, actChange, "Change is returned despite that the paid amount" +
				  " equals the running total.");
		assertEquals(amount, actPaidAmount, "The paid amount registered does not match " +
				  "the actual amount paid.");
	}
}

