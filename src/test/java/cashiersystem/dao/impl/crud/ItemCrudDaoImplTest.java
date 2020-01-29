package cashiersystem.dao.impl.crud;

import cashiersystem.entity.Item;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemCrudDaoImplTest {

    private static final ItemCrudDaoImpl itemCrudDao = new ItemCrudDaoImpl();

    @Test
    public void findByNameShouldBePresent() {
        Optional<Item> item = itemCrudDao.findByName("Kumquat");
        assertEquals(true, item.isPresent());

    }

    @Test
    public void findAllShouldBeGreaterThanZero() {
        int size = itemCrudDao.findAll().size();
        assertTrue(size > 0);
    }

    @Test
    public void countShouldNotEqualToZero() {
        long count = itemCrudDao.count();
        assertTrue(count != 0);
    }
}