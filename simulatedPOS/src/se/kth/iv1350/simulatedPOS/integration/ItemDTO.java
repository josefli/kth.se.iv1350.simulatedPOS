package se.kth.iv1350.simulatedPOS.integration;

/**
 * DTO class that represents the items in the database. Instances are immutable.
 */

public final class ItemDTO {

	public final String itemIdentifier;
	public final String itemDescription;
	public final double price;
	public final double tax;

	/**
	 * Creates a new item DTO.
	 */

	public ItemDTO(String ID, String description, double price, double tax){
		this.itemIdentifier = ID;
		this.itemDescription = description;
		this.price = price;
		this.tax = tax;
	}

	/**
	 * Compares an itemDTO to another to see if they are equal based on attributes.
	 * @param otherItem item to compare to.
	 * @return Boolean on whether all attributes are the same or not.
	 */

	public boolean equals(ItemDTO otherItem){
		if(!this.itemIdentifier.equals(otherItem.itemIdentifier)){
			return false;
		}
		if(!this.itemDescription.equals(otherItem.itemDescription)){
			return false;
		}
		if(this.price != otherItem.price){
			return false;
		}
		if(this.tax != otherItem.tax){
			return false;
		}
		return true;
	}
}
