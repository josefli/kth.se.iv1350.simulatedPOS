package se.kth.iv1350.simulatedPOS.integration;

import java.util.HashMap;
import java.util.Set;

import se.kth.iv1350.simulatedPOS.model.Sale;

/**
 * Represents the external inventory system.
 */

public class Inventory {

	HashMap<ItemDTO, Integer> inventoryList;
	public boolean inventoryUpdated = false;

	/**
	 * Creates an instance of the inventory system used as a reference.
	 */

	public Inventory() {
		this.inventoryList = new HashMap<>();
		setUpTestInventoryDatabase();
	}

	private void setUpTestInventoryDatabase() {
		ItemDTO milkDTO = new ItemDTO("00001", "Milk", 10, 5);
		ItemDTO butterDTO = new ItemDTO("00002", "Butter", 45, 10);
		ItemDTO flourDTO = new ItemDTO("00003", "Flour", 19, 7);
		ItemDTO eggDTO = new ItemDTO("00004", "Egg", 12.95, 12);
		ItemDTO pepsiMaxDTO = new ItemDTO("00005", "Pepsi max", 21.95, 6);

		addItemDTOToInventory(milkDTO);
		addItemDTOToInventory(butterDTO);
		addItemDTOToInventory(flourDTO);
		addItemDTOToInventory(eggDTO);
		addItemDTOToInventory(pepsiMaxDTO);
	}

	/**
	 * Adds an item to the inventory with default quantity 10.
	 *
	 * @param itemToAdd Item to add to inventory.
	 */

	private void addItemDTOToInventory(ItemDTO itemToAdd) {
		int quantityToAddToInventory = 10;
		this.inventoryList.put(itemToAdd, quantityToAddToInventory);
	}

	/**
	 * Updates the inventory based on sold items of the completed sale. An empty method.
	 *
	 * @param sale the sale based on which the inventory will be updated.
	 */

	public void updateInventory(Sale sale) {

		// Updates inventory based on items sold
		this.inventoryUpdated = true;
	}

	/**
	 * Searches the inventory database using the item identifier and returns the complete information of the item.
	 *
	 * @param itemID The item for which to search.
	 * @return The itemDTO of the requested item.
	 */

	public ItemDTO getItemDTO(String itemID) {
		Set<ItemDTO> itemDTOSet = inventoryList.keySet();

		for (ItemDTO itemDTO : itemDTOSet) {
			if (itemDTO.itemIdentifier.equals(itemID)) {
				return itemDTO;
			}
		}
		return null;
	}

	/**
	 * Searches the inventory database for a specific item based on item ID.
	 *
	 * @param itemID itemID to search for
	 * @return Boolean of whether the item was found or not.
	 */

	public boolean checkIfItemInInventory(String itemID) {
		Set<ItemDTO> itemDTOSet = inventoryList.keySet();

		for (ItemDTO itemDTO : itemDTOSet) {
			if (itemDTO.itemIdentifier.equals(itemID)) {
				return true;
			}
		}
		return false;
	}
}