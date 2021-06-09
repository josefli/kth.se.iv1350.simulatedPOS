package se.kth.iv1350.simulatedPOS.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemDTOTest {
	ItemDTO firstItem;
	ItemDTO secondItem;

	@BeforeEach
	void setUp(){
		this.firstItem = new ItemDTO("1", "Tape", 3, 7);
		this.secondItem = new ItemDTO("1", "Tape", 3, 7);
	}

	@Test
	void testEquals() {
		boolean acctResult = this.firstItem.equals(secondItem);
		boolean expResult = true;
		assertEquals(acctResult, expResult, "Items are not equal.");
	}

	@Test
	void testNotEqualsID() {
		this.secondItem = new ItemDTO("2", "Tape", 3, 7);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different IDs.");
	}

	@Test
	void testNotEqualsDescription() {
		this.secondItem = new ItemDTO("1", "Honey", 3, 7);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different descriptions.");
	}

	@Test
	void testNotEqualsPrice() {
		this.secondItem = new ItemDTO("1", "Tape", 100, 7);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different prices.");
	}

	@Test
	void testNotEqualsTax() {
		this.secondItem = new ItemDTO("1", "Tape", 3, 25);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different VAT.");
	}

	@Test
	void testNotEqualsIDAndDescription() {
		this.secondItem = new ItemDTO("2", "Honey", 3, 7);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different IDs and descriptions.");
	}

	@Test
	void testNotEqualsIDAndPrice() {
		this.secondItem = new ItemDTO("2", "Tape", 70, 7);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different IDs and prices.");
	}

	@Test
	void testNotEqualsIDAndTax() {
		this.secondItem = new ItemDTO("2", "Tape", 3, 79);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different IDs and VAT.");
	}

	@Test
	void testNotEqualsDescriptionAndPrice() {
		this.secondItem = new ItemDTO("1", "Honey", 68, 7);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different descriptions and prices.");
	}

	@Test
	void testNotEqualsDescriptionAndTax() {
		this.secondItem = new ItemDTO("1", "Honey", 3, 53);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different descriptions and VAT.");
	}

	@Test
	void testNotEqualsPriceAndTax() {
		this.secondItem = new ItemDTO("1", "Tape", 99, 53);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different prices and VAT.");
	}

	@Test
	void testNotEqualsIDAndDescriptionAndPrice() {
		this.secondItem = new ItemDTO("2", "Honey", 32, 7);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different IDs, descriptions and prices.");
	}

	@Test
	void testNotEqualsIDAndDescriptionAndTax() {
		this.secondItem = new ItemDTO("2", "Honey", 3, 0);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different IDs, descriptions and VAT.");
	}

	@Test
	void testNotEqualsDescriptionAndPriceAndTax() {
		this.secondItem = new ItemDTO("1", "Honey", 32, 47);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different descriptions, prices and VAT.");
	}

	@Test
	void testNotEqualsIDAndDescriptionAndPriceAndTax() {
		this.secondItem = new ItemDTO("2", "Honey", 32, 13);
		boolean acctResult = this.firstItem.equals(this.secondItem);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite different IDs, descriptions, prices and VAT.");
	}
}