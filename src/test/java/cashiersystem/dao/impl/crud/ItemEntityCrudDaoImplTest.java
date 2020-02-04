package cashiersystem.dao.impl.crud;

import cashiersystem.dao.ConnectionPool;
import cashiersystem.dao.domain.Page;
import cashiersystem.dao.impl.HikariCPManager;
import cashiersystem.entity.ItemEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ItemEntityCrudDaoImplTest extends AbstractEntityCrudDaoImplTest{

    private static final String H2_PROPERTIES = "h2db";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ItemPageableCrudDaoImpl itemCrudDao;
    private ConnectionPool connector;

    @Before
    public void init() {
        connector = new HikariCPManager(H2_PROPERTIES);
        itemCrudDao = new ItemPageableCrudDaoImpl(connector);
        initTestDb(connector);
    }

    @After
    public void close() {
        connector.closeConnection();
    }

    @Test
    public void findByNameShouldBePresent() {
        Optional<ItemEntity> item = itemCrudDao.findByName("Kumquat");
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
        ItemEntity itemEntityFromDb = itemCrudDao.findById(1).get();
        ItemEntity itemEntity = ItemEntity.builder()
                .withId(1L)
                .withName("Kumquat")
                .withQuantity(2)
                .withWeight(417.5)
                .build();
        assertEquals(itemEntity, itemEntityFromDb);
    }

    @Test
    public void findByIdShouldThrowNoSuchElementException() {
        expectedException.expect(NoSuchElementException.class);
        ItemEntity userFromDb = itemCrudDao.findById(0).get();
        assertNotEquals(ItemEntity.builder().build(), userFromDb);
    }

    @Test
    public void countShouldBeMoreThanZero() {
        assertTrue(itemCrudDao.count() > 0);
    }

    @Test
    public void shouldReturnMapWithItemNameToQuantity() {
        Map<String, Integer> itemToQuantity = itemCrudDao.getItemToQuantity();
        assertTrue(itemToQuantity.size() > 0);
    }
}