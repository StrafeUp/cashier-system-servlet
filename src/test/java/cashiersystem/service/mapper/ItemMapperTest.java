package cashiersystem.service.mapper;

import cashiersystem.dao.domain.Item;
import cashiersystem.entity.ItemEntity;
import org.junit.Assert;
import org.junit.Test;

public class ItemMapperTest {

    private final ItemMapper mapper = new ItemMapper();

    @Test
    public void mapDomainToEntityShouldReturnItemWithValidName() {
        Item item = Item.builder()
                .withId(1L)
                .withName("Kumquat")
                .build();
        ItemEntity itemEntity = mapper.mapDomainToEntity(item);
        Assert.assertEquals("Kumquat", itemEntity.getName());
    }

    @Test
    public void mapEntityToDomainShouldReturnItemWithValidName() {
        ItemEntity itemEntity = ItemEntity.builder()
                .withId(1L)
                .withName("Kumquat")
                .build();
        Item item = mapper.mapEntityToDomain(itemEntity);
        Assert.assertEquals("Kumquat", item.getName());
    }
}