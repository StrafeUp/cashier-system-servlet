package cashiersystem.dao.impl.crud;

import cashiersystem.entity.Roles;
import cashiersystem.entity.User;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserCrudDaoImplTest {

    private static final UserCrudDaoImpl userCrudDao = new UserCrudDaoImpl();

    @Test
    public void findByEmailShouldBePresent() {
        Optional<User> byEmail = userCrudDao.findByEmail("Hello@gmail.com");
        assertEquals(true, byEmail.isPresent());

    }

    @Test
    public void count() {
        long count = userCrudDao.count();
        assertEquals(2, count);
    }

    @Test
    public void findAll() {
        User user1 = User.builder()
                .withId(1)
                .withUsername("Hello123")
                .withEmail("Hello@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Roles.MERCHANDISER).build();

        User user2 = User.builder()
                .withId(3)
                .withUsername("Hell12o123")
                .withEmail("Hello2@gmail.com")
                .withPassword("sagdhlsajb")
                .withRole(Roles.MERCHANDISER).build();

        List<User> allUsers = userCrudDao.findAll();
        assertThat(allUsers, Matchers.containsInAnyOrder(user1, user2));
    }
}