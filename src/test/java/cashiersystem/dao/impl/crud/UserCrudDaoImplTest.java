package cashiersystem.dao.impl.crud;

import cashiersystem.dao.domain.Page;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Role;
import cashiersystem.entity.User;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserCrudDaoImplTest {
    private final ConnectorDB connector = new ConnectorDB("database");
    private final UserPageableCrudDaoImpl userCrudDao = new UserPageableCrudDaoImpl(connector);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void findByEmailShouldBePresent() {
        Optional<User> byEmail = userCrudDao.findByEmail("Hello@gmail.com");
        assertTrue(byEmail.isPresent());

    }

    @Test
    public void findAllShouldReturnPage() {
        assertTrue(userCrudDao.findAll(new Page(1, 1)).size() > 0);
    }

    @Test
    public void findByIdShouldReturnUser() {
        User userFromDb = userCrudDao.findById(1).get();
        User user = User.builder()
                .withId(1L)
                .withUsername("Hello123")
                .withEmail("Hello@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Role.MERCHANDISER).build();
        assertEquals(user, userFromDb);
    }

    @Test
    public void findByIdShouldThrowSuchElement() {
        expectedException.expect(NoSuchElementException.class);
        User userFromDb = userCrudDao.findById(2).get();
        User user = User.builder()
                .withId(1L)
                .withUsername("Hello123")
                .withEmail("Hello@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Role.MERCHANDISER)
                .build();
        assertNotEquals(user, userFromDb);
    }

    @Test
    public void countShouldBeMoreThanZero() {
        assertTrue(userCrudDao.count() > 0);
    }

    @Test
    public void findAllShouldContainUsers() {
        User user1 = User.builder()
                .withId(1L)
                .withUsername("Hello123")
                .withEmail("Hello@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Role.MERCHANDISER)
                .build();

        User user2 = User.builder()
                .withId(3L)
                .withUsername("Hell12o123")
                .withEmail("Hello2@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Role.MERCHANDISER)
                .build();

        List<User> allUsers = userCrudDao.findAll();
        assertThat(allUsers, Matchers.containsInAnyOrder(user1, user2));
    }
}