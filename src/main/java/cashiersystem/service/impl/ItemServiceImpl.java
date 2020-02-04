package cashiersystem.service.impl;

import cashiersystem.dao.impl.crud.ItemPageableCrudDaoImpl;
import cashiersystem.entity.ItemEntity;

import java.util.Optional;

public class ItemServiceImpl {

    private final ItemPageableCrudDaoImpl itemPageableCrudDao;

    public ItemServiceImpl(ItemPageableCrudDaoImpl itemPageableCrudDao) {
        this.itemPageableCrudDao = itemPageableCrudDao;
    }

    public void addItemToVault(ItemEntity itemEntity) {
        Optional<ItemEntity> itemFromDb = itemPageableCrudDao.findByName(itemEntity.getName());
        itemFromDb.ifPresent(value -> itemPageableCrudDao.update(ItemEntity.builder()
                .withQuantity(value.getQuantity() + itemEntity.getQuantity())
                .withId(itemEntity.getId())
                .withName(itemEntity.getName())
                .withWeight(itemEntity.getWeight())
                .build()));
        itemPageableCrudDao.save(itemEntity);
    }

}
