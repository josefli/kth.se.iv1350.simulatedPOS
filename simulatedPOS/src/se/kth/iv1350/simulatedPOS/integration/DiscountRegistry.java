package se.kth.iv1350.simulatedPOS.integration;

import se.kth.iv1350.simulatedPOS.model.SaleDTO;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Represents the discount registry/database.
 */

public class DiscountRegistry {

	HashMap<DiscountDTO, String> discountList;

	/**
	 * Creates an instance of the discount registry used as a reference. Contains one store and one customer.
	 */

	public DiscountRegistry(){
		this.discountList = new HashMap<>();
		setUpTestDiscountRegistryDatabase();
	}

	private void setUpTestDiscountRegistryDatabase(){
		addDiscount("Customer", "10001", "Percent discount to milk", 20);
		addDiscount("Store", "20001", "Percent of whole purchase", 10);

	}

	private void addDiscount(String storeOrCustomer, String identification, String discountDescription, double discountAmount){
		DiscountDTO newDiscountDTO = new DiscountDTO(identification, discountDescription, discountAmount);

		this.discountList.put(newDiscountDTO, storeOrCustomer);
	}

	/**
	 * Searches the registry for a customer discount and returns a discountDTO with discount information linked
	 * to the customer.
	 *
	 * @param customerDTO the DTO containing information about the customer.
	 * @return a discountDTO containing the customer specific discounts.
	 */

	public DiscountDTO findCustomerDiscount(CustomerDTO customerDTO) {

		Set<DiscountDTO> discountDTOSet = discountList.keySet();
		for (DiscountDTO discountDTO : discountDTOSet) {
			if (discountDTO.identificationOfCustomerOrStore.equals(customerDTO.customerID)) {
				return discountDTO;
			}
		}
		return null;
	}

	/**
	 * Searches the registry for a store discount and returns a discountDTO with discount information linked
	 * to the store.
	 *
	 * @param saleDTO the DTO containing information about the customer.
	 * @return a discountDTO containing the store specific discounts.
	 */

	public DiscountDTO findStoreDiscount(SaleDTO saleDTO) {
		Set<DiscountDTO> discountDTOSet = discountList.keySet();
		for (DiscountDTO discountDTO : discountDTOSet) {
			if (discountDTO.identificationOfCustomerOrStore.equals(saleDTO.storeDTO.nameOfStore)) {
				return discountDTO;
			}
		}
		return null;
	}

}
