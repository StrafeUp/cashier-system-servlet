package cashiersystem.dao.impl.crud;

import cashiersystem.dao.ConnectionPool;
import cashiersystem.dao.domain.Page;
import cashiersystem.dao.impl.HikariCPManager;
import cashiersystem.entity.ItemEntity;
import cashiersystem.entity.ReceiptEntity;
import cashiersystem.entity.Role;
import cashiersystem.entity.Status;
import cashiersystem.entity.UserEntity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReceiptEntityPageableCrudDaoImplTest extends AbstractEntityCrudDaoImplTest {

    private static final String H2_PROPERTIES = "h2db";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ReceiptPageableCrudDaoImpl receiptPageableCrudDao;
    private ItemPageableCrudDaoImpl itemPageableCrudDao;

    @Before
    public void init() {
        ConnectionPool connector = new HikariCPManager(H2_PROPERTIES);
        receiptPageableCrudDao = new ReceiptPageableCrudDaoImpl(connector);
        itemPageableCrudDao = new ItemPageableCrudDaoImpl(connector);
        initTestDb(connector);
    }

    @Test
    public void findAllByUser() {
        List<ReceiptEntity> allByUser = receiptPageableCrudDao.findAllByUser(UserEntity.builder()
                .withId(1L)
                .build());

        assertNotNull(allByUser);
    }

    @Test
    public void shouldSaveReceiptAndSubtract2Lobak() {
        ReceiptEntity receiptEntity = ReceiptEntity.builder()
                .withStatus(Status.OPEN)
                .withUser(UserEntity.builder()
                        .withId(1L)
                        .withUsername("Hello123")
                        .withEmail("Hello@gmail.com")
                        .withPassword("sagdhlsajb")
                        .withRole(Role.MERCHANDISER)
                        .build())
                .withItems(Arrays.asList(
                        ItemEntity.builder()
                                .withId(13L)
                                .withName("Lobak")
                                .withWeight(202.3)
                                .withQuantity(2)
                                .build(),
                        ItemEntity.builder()
                                .withId(1L)
                                .withName("Kumquat")
                                .withWeight(202.3)
                                .withQuantity(1)
                                .build()))
                .build();
        receiptPageableCrudDao.save(receiptEntity);
        Optional<ItemEntity> lobak = itemPageableCrudDao.findByName("Lobak");
        assertEquals(18, lobak.get().getQuantity());
    }

    @Test
    public void findByReceiptId() {
        ReceiptEntity byReceiptEntityId = receiptPageableCrudDao.findByReceiptId(3L);
        ReceiptEntity receiptEntity = ReceiptEntity.builder()
                .withStatus(Status.OPEN)
                .withItems(Collections.singletonList(ItemEntity.builder()
                        .withId(20L)
                        .withName("Caviar - Salmon")
                        .withWeight(202.3)
                        .withQuantity(80)
                        .build()))
                .build();
        assertEquals(receiptEntity, byReceiptEntityId);
    }

    @Test
    public void findByNameShouldBePresent() {
        Optional<ReceiptEntity> receipt = receiptPageableCrudDao.findById(1);
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
        ReceiptEntity receiptEntityFromDB = receiptPageableCrudDao.findById(1).get();
        ReceiptEntity receiptEntity = ReceiptEntity.builder()
                .withId(1L)
                .withReceiptId(1L)
                .withStatus(Status.IN_DISPUTE)
                .withUser(UserEntity.builder()
                        .withId(1L)
                        .build())
                .build();
        assertEquals(receiptEntity, receiptEntityFromDB);
    }

    @Test
    public void findByIdShouldThrowSuchElement() {
        expectedException.expect(NoSuchElementException.class);
        ReceiptEntity userFromDb = receiptPageableCrudDao.findById(0).get();
        assertNotEquals(ReceiptEntity.builder().build(), userFromDb);
    }

    @Test
    public void countShouldBeMoreThanZero() {
        assertTrue(receiptPageableCrudDao.count() > 0);
    }
}