package cashiersystem.dao.impl.crud;

import cashiersystem.dao.ConnectionPool;
import cashiersystem.dao.domain.Page;
import cashiersystem.dao.impl.HikariCPManager;
import cashiersystem.entity.Role;
import cashiersystem.entity.UserEntity;
import org.hamcrest.Matchers;
import org.junit.Before;
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

public class UserEntityCrudDaoImplTest extends AbstractEntityCrudDaoImplTest {

    private static final String H2_PROPERTIES = "h2db";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private UserPageableCrudDaoImpl userPageableCrudDao;

    @Before
    public void init() {
        ConnectionPool connector = new HikariCPManager(H2_PROPERTIES);
        userPageableCrudDao = new UserPageableCrudDaoImpl(connector);
        initTestDb(connector);
    }

    @Test
    public void findByEmailShouldBePresent() {
        Optional<UserEntity> byEmail = userPageableCrudDao.findByEmail("Hello@gmail.com");
        assertTrue(byEmail.isPresent());

    }

    @Test
    public void findAllShouldReturnPage() {
        assertTrue(userPageableCrudDao.findAll(new Page(1, 1)).size() > 0);
    }

    @Test
    public void findByIdShouldReturnUser() {
        UserEntity userEntityFromDb = userPageableCrudDao.findById(1).get();
        UserEntity userEntity = UserEntity.builder()
                .withId(1L)
                .withUsername("Hello123")
                .withEmail("Hello@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Role.MERCHANDISER).build();
        assertEquals(userEntity, userEntityFromDb);
    }

    @Test
    public void findByIdShouldThrowSuchElement() {
        expectedException.expect(NoSuchElementException.class);
        UserEntity userEntityFromDb = userPageableCrudDao.findById(2).get();
        UserEntity userEntity = UserEntity.builder()
                .withId(1L)
                .withUsername("Hello123")
                .withEmail("Hello@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Role.MERCHANDISER)
                .build();
        assertNotEquals(userEntity, userEntityFromDb);
    }

    @Test
    public void countShouldBeMoreThanZero() {
        assertTrue(userPageableCrudDao.count() > 0);
    }

    @Test
    public void findAllShouldContainUsers() {
        UserEntity userEntity1 = UserEntity.builder()
                .withId(1L)
                .withUsername("Hello123")
                .withEmail("Hello@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Role.MERCHANDISER)
                .build();

        UserEntity userEntity2 = UserEntity.builder()
                .withId(3L)
                .withUsername("Hell12o123")
                .withEmail("Hello2@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Role.MERCHANDISER)
                .build();

        List<UserEntity> allUserEntities = userPageableCrudDao.findAll();
        assertThat(allUserEntities, Matchers.containsInAnyOrder(userEntity1, userEntity2));
    }
}