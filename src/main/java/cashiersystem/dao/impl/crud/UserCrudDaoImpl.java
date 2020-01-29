package cashiersystem.dao.impl.crud;

import cashiersystem.dao.UserCrudDao;
import cashiersystem.dao.exception.DataBaseSqlRuntimeException;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Roles;
import cashiersystem.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserCrudDaoImpl extends AbstractCrudDao<User> implements UserCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(UserCrudDaoImpl.class);

    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM cashierSystem.users WHERE email=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM cashierSystem.users WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM cashierSystem.users";
    private static final String FIND_ALL_QUERY_LIMIT = "SELECT * FROM cashierSystem.users LIMIT ?, ?";
    private static final String COUNT_ALL_ROWS = "SELECT COUNT(*) FROM cashierSystem.users";
    private static final String SAVE_USER_QUERY = "INSERT INTO cashierSystem.users (username, email, password, role_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM cashierSystem.users WHERE id =?";
    private static final String UPDATE_USER_QUERY = "UPDATE cashierSystem.users SET username =?, email=?, password=?, role_id=? WHERE id = ?";


    public UserCrudDaoImpl() {
        super(FIND_BY_ID_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findByParam(email, FIND_BY_EMAIL_QUERY, STRING_PARAM_SETTER);
    }

    @Override
    public void save(User entity) {
        try (final PreparedStatement preparedStatement = ConnectorDB.getConnection().prepareStatement(SAVE_USER_QUERY)) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setInt(4, entity.getRole().ordinal());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR, e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }

    @Override
    public List<User> findAll(int page, int itemsPerPage) {
        try (final PreparedStatement preparedStatement =
                     ConnectorDB.getConnection().prepareStatement(FIND_ALL_QUERY_LIMIT)) {
            preparedStatement.setInt(1, (page - 1) * itemsPerPage);
            preparedStatement.setInt(2, itemsPerPage);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    final User optionalUser = mapResultSetToEntity(resultSet);
                    users.add(optionalUser);
                }
                return users;
            }

        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR, e));
            throw new DataBaseSqlRuntimeException("", e);

        }
    }

    @Override
    public long count() {
        return super.count(COUNT_ALL_ROWS);
    }

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        System.out.println(resultSet.getString("username"));
        return User.builder()
                .withId(resultSet.getLong("id"))
                .withUsername(resultSet.getString("username"))
                .withPassword(resultSet.getString("password"))
                .withEmail(resultSet.getString("email"))
                .withRole(Roles.values()[(resultSet.getInt("role_id")) - 1]).build();
    }

    @Override
    public List<User> findAll() {
        try (final PreparedStatement preparedStatement =
                     ConnectorDB.getConnection().prepareStatement(FIND_ALL_QUERY)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    final User optionalUser = mapResultSetToEntity(resultSet);
                    users.add(optionalUser);
                }
                return users;
            }
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR, e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }

    @Override
    public void update(User entity) {
        try (final PreparedStatement preparedStatement = ConnectorDB.getConnection().prepareStatement(UPDATE_USER_QUERY)) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setLong(3, entity.getRole().ordinal());
            preparedStatement.setLong(5, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR, e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }
}
