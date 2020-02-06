package cashiersystem.service;

import cashiersystem.dao.domain.Item;
import cashiersystem.dao.domain.Receipt;

public interface ReceiptService extends PageableService<Receipt> {

    void saveReceipt(Receipt receipt);

    Receipt getByReceiptId(Long receiptId);

    void removeItemFromReceipt(Long receiptId, Item item);
}
