package se.kth.iv1350.simulatedPOS.model;

import se.kth.iv1350.simulatedPOS.integration.ItemDTO;

import java.util.Set;

/**
 * DTO class that contains the information of the receipt formatted as a receipt.
 */

public final class ReceiptDTO {

	private StringBuilder storeInfo = new StringBuilder();
	private StringBuilder itemSection = new StringBuilder();
	private StringBuilder paymentSection = new StringBuilder();
	public StringBuilder fullReceipt = new StringBuilder("\t\t --- RECEIPT ---\n\n");

	/**
	 * Creates a receipt DTO from a sale and formats the information.
	 * @param saleDTO Sale to present as a receipt.
	 */

	ReceiptDTO(SaleDTO saleDTO) {

		this.storeInfo.append("\t\t\t").append(saleDTO.storeDTO.nameOfStore).append("\n");
		this.storeInfo.append(saleDTO.storeDTO.streetNumber).append(" ")
				  .append(saleDTO.storeDTO.streetName).append(" ").append(saleDTO.storeDTO.zipCode)
				  .append(", ").append(saleDTO.storeDTO.city).append("\nTime: ")
				  .append(saleDTO.timeOfSale.toLocalDate()).append(" ").append(saleDTO.timeOfSale.getHour())
				  .append(":").append(saleDTO.timeOfSale.getMinute()).append("\n--------------------------------\n");

		Set<ItemDTO> itemKeySet = saleDTO.soldItems.keySet();
		for (ItemDTO item : itemKeySet) {
			itemSection.append(item.itemDescription).append("\t\t\t")
					  .append(saleDTO.soldItems.get(item)).append(" * ").append(item.price)
					  .append("\t").append(saleDTO.soldItems.get(item) * item.price).append("\n");
		}

		this.paymentSection.append("\n--------------------------------\nTotal: \t\t\t\t\t" + " ")
				  .append(saleDTO.runningTotal).append("SEK\n").append("VAT: \t\t\t\t\t   ")
				  .append(saleDTO.totalTaxOfPurchase).append("%\n\n").append("Amount paid: \t\t\t   ")
				  .append(saleDTO.payment.amountPaid).append("\n").append("Change: \t\t\t\t    ")
				  .append(saleDTO.payment.change);

		this.fullReceipt.append(this.storeInfo).append(this.itemSection).append(this.paymentSection)
				  .append("\n\n\t\t Have a nice day!");
	}
}
