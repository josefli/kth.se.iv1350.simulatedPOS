package se.kth.iv1350.simulatedPOS.integration;

/**
 * DTO class that represents a store in the database. Instances are immutable.
 */

public class StoreDTO {

	public final String nameOfStore;
	public final String streetName;
	public final String streetNumber;
	public final String zipCode;
	public final String city;

	/**
	 * Creates a new store DTO.
	 * @param storeName name of store
	 * @param streetAddress street name part of address
	 * @param streetNumber street number of address
	 * @param zipCode zip code of address
	 * @param city the city in which the store is located
	 */

	public StoreDTO(String storeName, String streetAddress, String streetNumber, String zipCode, String city){
		this.nameOfStore = storeName;
		this.streetName = streetAddress;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
	}
}
