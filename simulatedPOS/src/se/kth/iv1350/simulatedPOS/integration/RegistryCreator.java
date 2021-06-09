package se.kth.iv1350.simulatedPOS.integration;

/**
 *  Represents the registry creator. Creates all registries and their references.
 */

public class RegistryCreator {

	private Accounting accounting;
	private Inventory inventory;

	/**
	 * Creates a new registry creator as well as new registries and external systems.
	 */

	public RegistryCreator(){

		this.accounting = new Accounting();
		this.inventory = new Inventory();

	}

	/**
	 * Getter for an item DTO.
	 *
	 * @param itemIdentifier item to be retrieved.
	 * @return item DTO for the requested item.
	 */

	public ItemDTO getItemDTO(String itemIdentifier) {
		return inventory.getItemDTO(itemIdentifier);
	}

	/**
	 * Getter for the inventory system.
	 *
	 * @return Reference to the inventory.
	 */

	public Inventory getInventory(){
		return this.inventory;
	}

	/**
	 * Getter for the accounting system.
	 *
	 * @return Reference to the accounting.
	 */

	public Accounting getAccounting(){
		return this.accounting;
	}
}
