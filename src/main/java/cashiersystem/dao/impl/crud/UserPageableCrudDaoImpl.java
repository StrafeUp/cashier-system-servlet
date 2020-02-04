package cashiersystem.dao.impl.crud;

import cashiersystem.dao.ConnectionPool;
import cashiersystem.dao.UserCrudDao;
import cashiersystem.entity.Role;
import cashiersystem.entity.UserEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserPageableCrudDaoImpl extends AbstractPageableCrudDao<UserEntity> implements UserCrudDao {
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_ALL_QUERY_LIMIT = "SELECT * FROM users LIMIT ?, ?";
    private static final String COUNT_ALL_ROWS = "SELECT COUNT(*) FROM users";
    private static final String SAVE_USER_QUERY = "INSERT INTO users (username, email, password, role_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM users WHERE id =?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET username =?, email=?, password=?, role_id=? WHERE id = ?";

    public UserPageableCrudDaoImpl(ConnectionPool connectionPool) {
        super(FIND_BY_ID_QUERY, DELETE_BY_ID_QUERY, COUNT_ALL_ROWS,
                FIND_ALL_QUERY, FIND_ALL_QUERY_LIMIT, SAVE_USER_QUERY, UPDATE_USER_QUERY, connectionPool);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return findByParam(email, FIND_BY_EMAIL_QUERY, STRING_PARAM_SETTER);
    }

    @Override
    protected UserEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return UserEntity.builder()
                .withId(resultSet.getLong("id"))
                .withUsername(resultSet.getString("username"))
                .withPassword(resultSet.getString("password"))
                .withEmail(resultSet.getString("email"))
                .withRole(Role.getRoleById(resultSet.getInt("role_id"))).build();
    }

    @Override
    protected void prepareEntity(UserEntity entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getUsername());
        preparedStatement.setString(2, entity.getEmail());
        preparedStatement.setString(3, entity.getPassword());
        preparedStatement.setInt(4, entity.getRole().ordinal());
    }

    @Override
    protected void prepareEntityWithId(UserEntity entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getUsername());
        preparedStatement.setString(2, entity.getEmail());
        preparedStatement.setString(3, entity.getPassword());
        preparedStatement.setLong(4, entity.getRole().getId());
        preparedStatement.setLong(5, entity.getId());
    }

}