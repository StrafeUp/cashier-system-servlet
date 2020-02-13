package cashiersystem.service.impl;

import cashiersystem.dao.ItemCrudDao;
import cashiersystem.dao.domain.Item;
import cashiersystem.entity.ItemEntity;
import cashiersystem.service.exception.EntityNotFoundException;
import cashiersystem.service.mapper.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceImplTest {

    public static final String ITEM_NAME = "Kumquat";
    private static final Item ITEM = Item.builder()
            .withId(1L)
            .build();
    private static final ItemEntity ITEM_ENTITY = ItemEntity.builder()
            .withId(1L)
            .build();

    @Mock
    private ItemCrudDao itemPageableCrudDao;
    @Mock
    private Mapper<ItemEntity, Item> itemMapper;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void getItemByIdShouldReturnValidItem() {
        when(itemPageableCrudDao.findById(anyInt())).thenReturn(Optional.of(ITEM_ENTITY));
        when(itemMapper.mapEntityToDomain(any())).thenReturn(ITEM);
        itemService.getItemById(1);
        verify(itemMapper, Mockito.times(1)).mapEntityToDomain(eq(ITEM_ENTITY));
    }

    @Test
    public void getItemByNameShouldReturnValidItem() {
        when(itemPageableCrudDao.findByName(anyString())).thenReturn(Optional.of(ITEM_ENTITY));
        when(itemMapper.mapEntityToDomain(any())).thenReturn(ITEM);
        itemService.getItemByName(ITEM_NAME);
        verify(itemMapper, Mockito.times(1)).mapEntityToDomain(eq(ITEM_ENTITY));
    }

    @Test
    public void countShouldReturnNumber() {
        when(itemPageableCrudDao.count()).thenReturn(1L);
        itemService.count();
        verify(itemPageableCrudDao, times(1)).count();
    }

    @Test
    public void addItemShouldProceed() {
        doNothing().when(itemPageableCrudDao).save(ITEM_ENTITY);

        itemService.addItem(ITEM_ENTITY);

        verifyZeroInteractions(itemMapper);
        verify(itemPageableCrudDao, times(1)).save(eq(ITEM_ENTITY));
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateItemShouldThrowEntityNotFoundException() {
        when(itemPageableCrudDao.findById(anyInt())).thenReturn(Optional.empty());
        itemService.updateItem(ITEM_ENTITY);
    }
}