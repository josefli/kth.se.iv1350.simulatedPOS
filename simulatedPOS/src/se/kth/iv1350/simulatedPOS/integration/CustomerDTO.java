package se.kth.iv1350.simulatedPOS.integration;

/**
 * DTO class that represents a customer in the system. Instances are immutable.
 */

public final class CustomerDTO {
	private final String customerName;
	public final String customerID;

	/**
	 * Creates a new customer DTO.
	 */

	public CustomerDTO(String name, String ID){
		this.customerName = name;
		this.customerID = ID;
	}
}
