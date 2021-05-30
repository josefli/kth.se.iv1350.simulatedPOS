package se.kth.iv1350.simulatedPOS.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
	private Inventory inventory;
	private ItemDTO milkItemDTO;
	private ItemDTO juiceItemDTO;

	@BeforeEach
	void setUp() {
		this.inventory = new Inventory();
		this.milkItemDTO = new ItemDTO("00001", "Milk", 10, 5);
		this.juiceItemDTO = new ItemDTO("00006", "Juice", 12, 7);
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testGetItemDTOSameItem() {
		ItemDTO fetchedItem = this.inventory.getItemDTO(this.milkItemDTO.itemIdentifier);
		boolean acctResult = fetchedItem.equals(this.milkItemDTO);
		boolean expResult = true;
		assertEquals(acctResult, expResult, "Items are not equal.");
	}

	@Test
	void testGetItemDTOSameID() {
		boolean acctResult = this.inventory.getItemDTO("00001").equals(this.milkItemDTO);
		boolean expResult = true;
		assertEquals(acctResult, expResult, "Items are not equal.");
	}

	@Test
	void testGetItemDTONotSameID() {
		boolean acctResult = this.inventory.getItemDTO("00002").equals(this.milkItemDTO);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Items are equal despite having different itemIDs.");
	}

	@Test
	void testAddItemDTOToInventoryNewItem() {
		this.inventory.addItemDTOToInventory(this.juiceItemDTO);
		boolean acctResult = this.inventory.inventoryList.containsKey(this.juiceItemDTO);
		boolean expResult = true;
		assertEquals(acctResult, expResult, "Item was not found in inventory despite being added.");
	}

	@Test
	void testAddItemDTOToInventoryExistingItem() {
		this.inventory.addItemDTOToInventory(this.milkItemDTO);
		boolean acctResult = this.inventory.inventoryList.containsKey(this.milkItemDTO);
		boolean expResult = true;
		assertEquals(acctResult, expResult, "Item was not found in inventory despite being added.");
	}

	@Test
	void testNotAddItemDTOToInventory() {
		boolean acctResult = this.inventory.inventoryList.containsKey(this.juiceItemDTO);
		boolean expResult = false;
		assertEquals(acctResult, expResult, "Item was found in inventory without being added.");
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
}