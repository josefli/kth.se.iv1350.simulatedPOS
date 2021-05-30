package se.kth.iv1350.simulatedPOS.model;

/**
 * DTO class that contains the information about the payment.
 */

public class PaymentDTO {
	final double amountPaid;
	final double change;

	/**
	 * Creates a new payment DTO.
	 *
	 * @param amountPaid The amount of cash the customer has paid.
	 * @param change The change returned to the customer.
	 */

	PaymentDTO(double amountPaid, double change){
		this.amountPaid = amountPaid;
		this.change = change;
	}

	/**
	 * Getter method for amount paid.
	 * @return amount paid.
	 */
	public double getAmountPaid() {
		return this.amountPaid;
	}
}
