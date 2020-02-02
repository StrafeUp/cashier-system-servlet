package cashiersystem.dao.impl.crud;

import cashiersystem.dao.domain.Page;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Item;
import cashiersystem.entity.Receipt;
import cashiersystem.entity.Status;
import cashiersystem.entity.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReceiptPageableCrudDaoImplTest {

    private static final String H2_PROPERTIES = "h2db";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ReceiptPageableCrudDaoImpl receiptPageableCrudDao;

    @Before
    public void init() {
        ConnectorDB connector = new ConnectorDB(H2_PROPERTIES);
        receiptPageableCrudDao = new ReceiptPageableCrudDaoImpl(connector);

        try {
            Connection connection = connector.getConnection();
            final Statement executeStatement = connection.createStatement();
            String schemaQuery = new String(Files.readAllBytes(Paths.get("src/test/resources/schema.sql")));
            System.out.println(schemaQuery);
            executeStatement.execute(schemaQuery);
            String dataQuery = new String(Files.readAllBytes(Paths.get("src/test/resources/data.sql")));
            executeStatement.execute(dataQuery);
            executeStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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