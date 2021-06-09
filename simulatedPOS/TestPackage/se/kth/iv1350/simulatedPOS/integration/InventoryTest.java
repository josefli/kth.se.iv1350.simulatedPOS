package se.kth.iv1350.simulatedPOS.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.simulatedPOS.model.Sale;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
	private Inventory inventory;
	private ItemDTO milkItemDTO = new ItemDTO("00001", "Milk", 10, 5);
	private ItemDTO juiceItemDTO = new ItemDTO("00006", "Juice", 12, 7);
	private Sale sale = new Sale(new Accounting());

	@BeforeEach
	void setUp() {
		this.inventory = new Inventory();

	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testCheckIfItemInInventory(){
		boolean acctResult = this.inventory.checkIfItemInInventory(this.milkItemDTO.itemIdentifier);
		boolean expResult = true;
		assertEquals(acctResult, expResult, "Item was not found despite being in inventory.");
	}

	@Test
	void testCheckIfItemInInventoryNot(){
		boolean acctResult = this.inventory.checkIfItemInInventory(this.juiceItemDTO.itemIdentifier);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Item was found despite not being in inventory.");
	}

	@Test
	void testUpdateInventoryFalse(){
		boolean expResult = false;
		boolean actResult = this.inventory.inventoryUpdated;
		assertEquals(expResult, actResult, "Inventory set as updated despite not being updated.");
	}

	@Test
	void testLoggedSaleSetToTrue(){
		boolean expResult = true;
		this.inventory.updateInventory(this.sale);
		boolean actResult = this.inventory.inventoryUpdated;
		assertEquals(expResult, actResult, "Inventory not set as updated despite being updated.");
	}
}