package se.kth.iv1350.simulatedPOS.integration;

import java.util.HashMap;
import java.util.Set;

/**
 * Represents the customer registry/database.
 */

public class CustomerRegistry {

	HashMap <CustomerDTO, String> customerList;

	/**
	 * Creates an instance of the customer registry used as a reference.
	 */

	public CustomerRegistry(){
		this.customerList = new HashMap<>();
		setUpTestCustomerDatabase();
	}

	private void setUpTestCustomerDatabase(){
		addCustomerToCustomerDatabase("10001", "Aron Aronsson");
		addCustomerToCustomerDatabase("10002", "Ben Benson");
		addCustomerToCustomerDatabase("10003", "Carl Carlson");
	}

	private void addCustomerToCustomerDatabase(String ID, String name){
		CustomerDTO customerToAdd = new CustomerDTO(ID, name);

	}

	/**
	 * Searches the registry for a specific customer based on their ID and returns the complete customer information.
	 *
	 * @param customerID the ID to search for in the registry.
	 * @return the information of the customer searched for.
	 */

	public CustomerDTO findCustomer(String customerID) {
		Set<CustomerDTO> customerDTOSet = customerList.keySet();
		for(CustomerDTO customerDTO : customerDTOSet) {
			if(customerDTO.customerID.equals(customerID)) {
				return customerDTO;
			}
		}

		return null;
	}
}
