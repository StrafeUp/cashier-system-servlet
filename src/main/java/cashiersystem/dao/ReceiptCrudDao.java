package cashiersystem.dao;

import cashiersystem.entity.Receipt;
import cashiersystem.entity.User;

import java.util.List;

public interface ReceiptCrudDao extends CrudPageableDao<Receipt> {

    List<Receipt> findAllByUser(User user);

    Receipt findByReceiptId(Long receiptId);
}
