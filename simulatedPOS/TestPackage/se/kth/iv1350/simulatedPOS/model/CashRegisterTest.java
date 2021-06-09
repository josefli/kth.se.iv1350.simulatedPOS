package se.kth.iv1350.simulatedPOS.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.simulatedPOS.integration.Accounting;
import se.kth.iv1350.simulatedPOS.integration.ItemDTO;

import static org.junit.jupiter.api.Assertions.*;

class CashRegisterTest {
	Accounting accounting = new Accounting();
	CashRegister cashRegister = new CashRegister();
	Sale sale = new Sale(accounting);

	@BeforeEach
	public void setUp(){
		this.sale.addSoldItem(new ItemDTO("0001", "Milk", 12, 5));
	}

	@Test
	void testAddPaymentCorrectBalance() {
		double amountPaid = 50;

		double expResult = this.cashRegister.balance + this.sale.runningTotal;
		this.cashRegister.addPayment(amountPaid, this.sale.getSaleDTO());
		double acctResult = this.cashRegister.balance;
		assertEquals(acctResult, expResult, "Balance was not updated correctly.");
	}

	@Test
	void testAddPaymentCorrectChange() {
		double amountPaid = 100;

		double acctResult = this.cashRegister.addPayment(amountPaid, this.sale.getSaleDTO());
		this.sale.registerPayment(amountPaid);
		double expResult = amountPaid - this.sale.runningTotal;

		assertEquals(acctResult, expResult, "Change was not returned correctly.");
	}

	@Test
	void testAddPaymentNoChange() {
		double amountPaid = this.sale.getSaleDTO().runningTotal;
		double acctResult = this.cashRegister.addPayment(amountPaid, this.sale.getSaleDTO());
		this.sale.registerPayment(amountPaid);
		double expResult = 0;

		assertEquals(acctResult, expResult, "Change was not set to 0 when payment was even.");
	}
}