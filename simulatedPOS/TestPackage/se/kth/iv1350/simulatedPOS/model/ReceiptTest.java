package se.kth.iv1350.simulatedPOS.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.simulatedPOS.integration.ItemDTO;
import se.kth.iv1350.simulatedPOS.integration.RegistryCreator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {
	RegistryCreator registryCreator = new RegistryCreator();
	Sale sale = new Sale(this.registryCreator.getAccounting());
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	PrintStream printStream = new PrintStream(outputStream);
	PrintStream old = System.out;

	@BeforeEach
	public void beforeEach(){

		System.setOut(printStream);
		this.sale.addSoldItem(this.registryCreator.getItemDTO("00001"));
		this.sale.addSoldItem(this.registryCreator.getItemDTO("00002"));
		this.sale.addSoldItem(this.registryCreator.getItemDTO("00003"));
		this.sale.addSoldItem(this.registryCreator.getItemDTO("00001"));

		this.sale.registerPayment(200);
	}

	@Test
	void testEmptyReceiptContainsAllSections(){
		this.sale = new Sale(this.registryCreator.getAccounting());
		this.sale.registerPayment(100);
		LocalDateTime saleTime = LocalDateTime.now();

		System.out.flush();
		System.setOut(old);
		String actResult = outputStream.toString();

		assertTrue(actResult.contains("VAT"), "Receipt lacks VAT.");
		assertTrue(actResult.contains("Total"), "Receipt lacks Total.");
		assertTrue(actResult.contains(this.sale.storeDTO.nameOfStore), "Receipt lacks name of store.");
		assertTrue(actResult.contains(this.sale.storeDTO.city), "Receipt lacks city.");
		assertTrue(actResult.contains(this.sale.storeDTO.zipCode), "Receipt lacks zip code.");
		assertTrue(actResult.contains(this.sale.storeDTO.streetName), "Receipt lacks street.");
		assertTrue(actResult.contains(this.sale.storeDTO.streetNumber), "Receipt lacks street number.");
		assertTrue(actResult.contains("" + saleTime.getYear()), "Receipt has the wrong year.");
		assertTrue(actResult.contains("" + saleTime.getMonthValue()), "Receipt has the wrong month.");
		assertTrue(actResult.contains("" + saleTime.getDayOfMonth()), "Receipt has the wrong day.");
		assertTrue(actResult.contains("" + saleTime.getHour()), "Receipt has the wrong hour.");
		assertTrue(actResult.contains("" + saleTime.getMinute()), "Receipt has the wrong minute.");
	}

	@Test
	void testReceiptVAT(){
		double VAT = this.sale.getSaleDTO().totalTaxOfPurchase;
		System.out.flush();
		System.setOut(old);
		String actResult = outputStream.toString();
		assertTrue(actResult.contains("" + VAT + "%"), "Receipt lacks correct VAT.");
	}

	@Test
	void testReceiptTotal(){
		double runningTotal = this.sale.getSaleDTO().runningTotal;
		System.out.flush();
		System.setOut(old);
		String actResult = outputStream.toString();
		assertTrue(actResult.contains("" + runningTotal), "Receipt lacks correct running total.");
	}

	@Test
	void testReceiptContainsAllItems(){
		String[] itemNames = new String[3];
		Set<ItemDTO> items = this.sale.soldItems.keySet();
		int j = 0;
		for (ItemDTO item : items) {
			itemNames[j] = item.itemDescription;
			j++;
		}

		System.out.flush();
		System.setOut(old);
		String actResult = outputStream.toString();

		for(int i = 0; i < 3; i++) {
			assertTrue(actResult.contains(itemNames[i]), "Receipt lacks item: " + itemNames[i]);
		}
	}

	@Test
	void testReceiptContainsCorrectQuantities(){
		String[] itemNames = new String[3];
		String[] itemPrices = new String[3];
		String[] itemQuantities = new String[3];
		Set<ItemDTO> items = this.sale.soldItems.keySet();
		int j = 0;
		for (ItemDTO item : items) {
			itemNames[j] = item.itemDescription;
			itemPrices[j] = "" + item.price;
			itemQuantities[j] = "" + this.sale.soldItems.get(item);
			j++;
		}

		System.out.flush();
		System.setOut(old);
		String actResult = outputStream.toString();

		for(int i = 0; i < 2; i++) {
			assertTrue(actResult.contains(itemNames[i] + "\t\t\t" + itemQuantities[i] + " * " + itemPrices[i]),
					  "Receipt has incorrect representation of " + itemNames[i]);
		}
	}

	@Test
	void testReceiptPaymentPresentation(){
		System.out.flush();
		System.setOut(old);
		String actResult = outputStream.toString();

		String expResult = "Amount paid: \t\t\t   " + this.sale.getSaleDTO().payment.amountPaid + "\n"
				  + "Change: \t\t\t\t    " + this.sale.getSaleDTO().payment.change;

		assertTrue(actResult.contains(expResult), "Receipt has incorrect formatting of the payment.");
	}
}