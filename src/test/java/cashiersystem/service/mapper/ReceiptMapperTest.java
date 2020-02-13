package cashiersystem.service.mapper;

import cashiersystem.dao.domain.Item;
import cashiersystem.dao.domain.Receipt;
import cashiersystem.dao.domain.User;
import cashiersystem.entity.ItemEntity;
import cashiersystem.entity.ReceiptEntity;
import cashiersystem.entity.UserEntity;
import cashiersystem.service.encoder.Encoder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptMapperTest {

    public static final Item ITEM = Item.builder().build();
    @Mock
    private Mapper<ItemEntity, Item> itemMapper;

    @Mock
    private Encoder passwordEncoder;

    @Mock
    private Mapper<UserEntity, User> userMapper;

    @InjectMocks
    private ReceiptMapper mapper;

    @Test
    public void mapDomainToEntityShouldReturnReceiptWithValidItemList() {
        when(itemMapper.mapEntityToDomain(any(ItemEntity.class))).thenReturn(ITEM);
        Receipt receipt = Receipt.builder()
                .withId(1L)
                .withUser(User.builder().withId(1L).build())
                .withItems(Collections.singletonList(itemMapper.mapEntityToDomain(ItemEntity.builder().build())))
                .build();
        ReceiptEntity receiptEntity = mapper.mapDomainToEntity(receipt);
        Assert.assertEquals(1, receiptEntity.getItemEntities().size());
    }

    @Test
    public void mapEntityToDomainShouldReturnReceiptWithValidItemList() {
        ReceiptEntity receiptEntity = ReceiptEntity.builder()
                .withId(1L)
                .withUser(UserEntity.builder().withId(1L).build())
                .withItems(Collections.singletonList(ItemEntity.builder().build()))
                .build();
        Receipt receipt = mapper.mapEntityToDomain(receiptEntity);
        Assert.assertEquals(1, receipt.getItems().size());
    }
}