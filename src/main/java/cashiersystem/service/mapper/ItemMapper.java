package cashiersystem.service.mapper;

import cashiersystem.dao.domain.Item;
import cashiersystem.entity.ItemEntity;

public class ItemMapper implements Mapper<ItemEntity, Item> {
    @Override
    public ItemEntity mapDomainToEntity(Item domain) {
        return ItemEntity.builder()
                .withId(domain.getId())
                .withName(domain.getName())
                .withQuantity(domain.getQuantity())
                .withWeight(domain.getWeight())
                .build();
    }

    @Override
    public Item mapEntityToDomain(ItemEntity entity) {
        return Item.builder()
                .withId(entity.getId())
                .withName(entity.getName())
                .withQuantity(entity.getQuantity())
                .withWeight(entity.getWeight())
                .build();
    }
}
