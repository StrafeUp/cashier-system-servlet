package cashiersystem.dao.impl.crud;

import cashiersystem.dao.domain.Page;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Role;
import cashiersystem.entity.User;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserCrudDaoImplTest {
    private static final String H2_PROPERTIES = "h2db";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private UserPageableCrudDaoImpl userPageableCrudDao;

    @Before
    public void init() {
        ConnectorDB connector = new ConnectorDB(H2_PROPERTIES);
        userPageableCrudDao = new UserPageableCrudDaoImpl(connector);

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
    public void findByEmailShouldBePresent() {
        Optional<User> byEmail = userPageableCrudDao.findByEmail("Hello@gmail.com");
        assertTrue(byEmail.isPresent());

    }

    @Test
    public void findAllShouldReturnPage() {
        assertTrue(userPageableCrudDao.findAll(new Page(1, 1)).size() > 0);
    }

    @Test
    public void findByIdShouldReturnUser() {
        User userFromDb = userPageableCrudDao.findById(1).get();
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
        User userFromDb = userPageableCrudDao.findById(2).get();
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
        assertTrue(userPageableCrudDao.count() > 0);
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

        List<User> allUsers = userPageableCrudDao.findAll();
        assertThat(allUsers, Matchers.containsInAnyOrder(user1, user2));
    }
}