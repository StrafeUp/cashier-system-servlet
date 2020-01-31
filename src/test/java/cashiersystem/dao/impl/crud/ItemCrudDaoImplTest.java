package cashiersystem.dao.impl.crud;

import cashiersystem.dao.domain.Page;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Item;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ItemCrudDaoImplTest {

    private static final String DATABASE_PROPERTIES = "database";

    private ConnectorDB connector = new ConnectorDB(DATABASE_PROPERTIES);
    private ItemPageableCrudDaoImpl itemCrudDao = new ItemPageableCrudDaoImpl(connector);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


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
    public void findByIdShouldThrowSuchElement() {
        expectedException.expect(NoSuchElementException.class);
        Item userFromDb = itemCrudDao.findById(0).get();
        assertNotEquals(Item.builder().build(), userFromDb);
    }

    @Test
    public void countShouldBeMoreThanZero() {
        assertTrue(itemCrudDao.count() > 0);
    }
}