package cashiersystem.service;

import cashiersystem.dao.domain.Item;
import cashiersystem.entity.ItemEntity;

public interface ItemService extends PageableService<Item> {

    void addItem(ItemEntity itemEntity);

    Item getItemById(Integer id);

    Item getItemByName(String name);

    void updateItem(ItemEntity itemEntity);

    void removeItemById(Integer id);
}
