package cashiersystem.dao;

import cashiersystem.entity.ReceiptEntity;
import cashiersystem.entity.UserEntity;

import java.util.List;

public interface ReceiptCrudDao extends CrudPageableDao<ReceiptEntity> {

    List<ReceiptEntity> findAllByUser(UserEntity userEntity);

    ReceiptEntity findByReceiptId(Long receiptId);
}
