package cashiersystem.dao.impl.crud;

import cashiersystem.dao.domain.Page;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Item;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ItemCrudDaoImplTest {

    private static final String H2_PROPERTIES = "h2db";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ItemPageableCrudDaoImpl itemCrudDao;

    @Before
    public void init() {
        ConnectorDB connector = new ConnectorDB(H2_PROPERTIES);
        itemCrudDao = new ItemPageableCrudDaoImpl(connector);

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
    public void findByNameShouldBePresent() {
        Optional<Item> item = itemCrudDao.findByName("Kumquat");
        assertTrue(item.isPresent());
    }

    @Test
    public void findAllShouldBeGreaterThanZero() {
        int size = itemCrudDao.findAll().size();
        assertTrue(size > 0);
    }

    @Test
    public void findAllShouldReturnPage() {
        assertTrue(itemCrudDao.findAll(new Page(1, 1)).size() > 0);
    }

    @Test
    public void findByIdShouldReturnItem() {
        Item itemFromDb = itemCrudDao.findById(1).get();
        Item item = Item.builder()
                .withId(1L)
                .withName("Kumquat")
                .withQuantity(2)
                .withWeight(417.5)
                .build();
        assertEquals(item, itemFromDb);
    }

    @Test
    public void findByIdShouldThrowNoSuchElementException() {
        expectedException.expect(NoSuchElementException.class);
        Item userFromDb = itemCrudDao.findById(0).get();
        assertNotEquals(Item.builder().build(), userFromDb);
    }

    @Test
    public void countShouldBeMoreThanZero() {
        assertTrue(itemCrudDao.count() > 0);
    }
}