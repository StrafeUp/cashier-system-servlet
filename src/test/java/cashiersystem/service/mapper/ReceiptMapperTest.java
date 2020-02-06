package cashiersystem.service.mapper;

import cashiersystem.dao.domain.Receipt;
import cashiersystem.entity.ItemEntity;
import cashiersystem.entity.ReceiptEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class ReceiptMapperTest {

    private final ReceiptMapper mapper = new ReceiptMapper();

    @Test
    public void mapDomainToEntityShouldReturnReceiptWithValidItemList() {
        Receipt receipt = Receipt.builder()
                .withId(1L)
                .withItems(Collections.singletonList(ItemEntity.builder().build()))
                .build();
        ReceiptEntity receiptEntity = mapper.mapDomainToEntity(receipt);
        Assert.assertEquals(1, receiptEntity.getItemEntities().size());
    }

    @Test
    public void mapEntityToDomainShouldReturnReceiptWithValidItemList() {
        ReceiptEntity receiptEntity = ReceiptEntity.builder()
                .withId(1L)
                .withItems(Collections.singletonList(ItemEntity.builder().build()))
                .build();
        Receipt receipt = mapper.mapEntityToDomain(receiptEntity);
        Assert.assertEquals(1, receipt.getItemEntities().size());
    }
}