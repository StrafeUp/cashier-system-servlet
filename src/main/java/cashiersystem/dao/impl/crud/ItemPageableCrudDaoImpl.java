package cashiersystem.dao.impl.crud;

import cashiersystem.dao.ConnectionPool;
import cashiersystem.dao.ItemCrudDao;
import cashiersystem.entity.ItemEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemPageableCrudDaoImpl extends AbstractPageableCrudDao<ItemEntity> implements ItemCrudDao {
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM items WHERE name=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM items WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM items";
    private static final String FIND_ALL_QUERY_LIMIT = "SELECT * FROM items LIMIT ?, ?";
    private static final String COUNT_ALL_ROWS = "SELECT COUNT(*) FROM items";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM items WHERE id =";
    private static final String SAVE_ITEM_QUERY = "INSERT INTO items (name, weight, quantity) VALUES (?,?,?);";
    private static final String UPDATE_ITEM_QUERY = "UPDATE items SET name =?, weight=?, quantity=? WHERE id = ?";

    public ItemPageableCrudDaoImpl(ConnectionPool connectionPool) {
        super(FIND_BY_ID_QUERY, DELETE_BY_ID_QUERY, COUNT_ALL_ROWS, FIND_ALL_QUERY,
                FIND_ALL_QUERY_LIMIT, SAVE_ITEM_QUERY, UPDATE_ITEM_QUERY, connectionPool);
    }

    @Override
    public Optional<ItemEntity> findByName(String name) {
        return findByParam(name, FIND_BY_NAME_QUERY, STRING_PARAM_SETTER);
    }

    public Map<String, Integer> getItemToQuantity() {
        List<ItemEntity> allItemEntities = findAll();
        return allItemEntities.stream().collect(Collectors.toMap(ItemEntity::getName, ItemEntity::getQuantity));
    }

    @Override
    protected ItemEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return ItemEntity.builder()
                .withId(resultSet.getLong("id"))
                .withName(resultSet.getString("name"))
                .withWeight(resultSet.getDouble("weight"))
                .withQuantity(resultSet.getInt("quantity"))
                .build();
    }

    @Override
    protected void prepareEntity(ItemEntity entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setDouble(2, entity.getWeight());
        preparedStatement.setInt(3, entity.getQuantity());
    }

    @Override
    protected void prepareEntityWithId(ItemEntity entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setDouble(2, entity.getWeight());
        preparedStatement.setInt(3, entity.getQuantity());
        preparedStatement.setLong(4, entity.getId());
    }
}
