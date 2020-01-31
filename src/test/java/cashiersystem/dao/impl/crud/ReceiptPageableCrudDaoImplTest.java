package cashiersystem.dao.impl.crud;

import cashiersystem.dao.domain.Page;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Item;
import cashiersystem.entity.Receipt;
import cashiersystem.entity.Status;
import cashiersystem.entity.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class  ReceiptPageableCrudDaoImplTest {

    private final ConnectorDB connector = new ConnectorDB("database");
    private final ReceiptPageableCrudDaoImpl receiptPageableCrudDao = new ReceiptPageableCrudDaoImpl(connector);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void findAllByUser() {
        List<Receipt> allByUser = receiptPageableCrudDao.findAllByUser(User.builder()
                .withId(1L)
                .build());

        assertNotNull(allByUser);
    }

    @Test
    public void findByReceiptId() {
        Receipt byReceiptId = receiptPageableCrudDao.findByReceiptId(3L);
        Receipt receipt = Receipt.builder()
                .withStatus(Status.OPEN)
                .withItems(Collections.singletonList(Item.builder()
                        .withId(20L)
                        .withName("Caviar - Salmon")
                        .withWeight(202.3)
                        .withQuantity(80)
                        .build()))
                .build();
        assertEquals(receipt, byReceiptId);
    }

    @Test
    public void findByNameShouldBePresent() {
        Optional<Receipt> receipt = receiptPageableCrudDao.findById(1);
        assertTrue(receipt.isPresent());

    }

    @Test
    public void findAllShouldBeGreaterThanZero() {
        int size = receiptPageableCrudDao.findAll().size();
        assertTrue(size > 0);
    }

    @Test
    public void findAllShouldReturnPage() {
        assertTrue(receiptPageableCrudDao.findAll(new Page(1, 1)).size() > 0);
    }

    @Test
    public void findByIdShouldReturnReceipt() {
        Receipt receiptFromDB = receiptPageableCrudDao.findById(1).get();
        Receipt receipt = Receipt.builder()
                .withId(1L)
                .withReceiptId(1L)
                .withStatus(Status.IN_DISPUTE)
                .withUser(User.builder()
                        .withId(1L)
                        .build())
                .build();
        assertEquals(receipt, receiptFromDB);
    }

    @Test
    public void findByIdShouldThrowSuchElement() {
        expectedException.expect(NoSuchElementException.class);
        Receipt userFromDb = receiptPageableCrudDao.findById(0).get();
        assertNotEquals(Receipt.builder().build(), userFromDb);
    }

    @Test
    public void countShouldBeMoreThanZero() {
        assertTrue(receiptPageableCrudDao.count() > 0);
    }
}