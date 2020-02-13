package cashiersystem.service.impl;

import cashiersystem.dao.ReceiptCrudDao;
import cashiersystem.dao.domain.Receipt;
import cashiersystem.entity.ReceiptEntity;
import cashiersystem.service.mapper.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptServiceImplTest {

    private static final Receipt RECEIPT = Receipt.builder().build();
    private static final ReceiptEntity RECEIPT_ENTITY = ReceiptEntity.builder().build();

    @Mock
    private ReceiptCrudDao receiptCrudDao;
    @Mock
    private Mapper<ReceiptEntity, Receipt> receiptMapper;

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @Test
    public void saveReceiptShouldSucceed() {
        doNothing().when(receiptCrudDao).save(any(ReceiptEntity.class));
        when(receiptMapper.mapDomainToEntity(any(Receipt.class))).thenReturn(RECEIPT_ENTITY);
        receiptService.saveReceipt(RECEIPT);
        verify(receiptMapper, times(1)).mapDomainToEntity(eq(RECEIPT));
    }

    @Test
    public void getByReceiptIdShouldReturnReceipt() {
        when(receiptCrudDao.findByReceiptId(1L)).thenReturn(RECEIPT_ENTITY);
        receiptService.getByReceiptId(1L);
        verify(receiptCrudDao).findByReceiptId(eq(1L));
    }

    @Test
    public void countShouldReturnNumber() {
        when(receiptCrudDao.count()).thenReturn(1L);
        receiptService.count();
        verify(receiptCrudDao).count();
    }
}