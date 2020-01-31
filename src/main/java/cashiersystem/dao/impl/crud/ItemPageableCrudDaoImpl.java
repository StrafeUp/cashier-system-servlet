package cashiersystem.dao.impl.crud;

import cashiersystem.dao.ItemCrudDao;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ItemPageableCrudDaoImpl extends AbstractPageableCrudDao<Item> implements ItemCrudDao {
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM cashierSystem.items WHERE name=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM cashierSystem.items WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM cashierSystem.items";
    private static final String FIND_ALL_QUERY_LIMIT = "SELECT * FROM cashierSystem.items LIMIT ?, ?";
    private static final String COUNT_ALL_ROWS = "SELECT COUNT(*) FROM cashierSystem.items";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM cashierSystem.items WHERE id =";
    private static final String SAVE_ITEM_QUERY = "INSERT INTO cashierSystem.items (name, weight, quantity) values (?,?,?);";
    private static final String UPDATE_ITEM_QUERY = "UPDATE cashierSystem.items SET name =?, weight=?, quantity=? WHERE id = ?";

    public ItemPageableCrudDaoImpl(ConnectorDB connectorDB) {
        super(FIND_BY_ID_QUERY, DELETE_BY_ID_QUERY, COUNT_ALL_ROWS, FIND_ALL_QUERY,
                FIND_ALL_QUERY_LIMIT, SAVE_ITEM_QUERY, UPDATE_ITEM_QUERY, connectorDB);
    }

    @Override
    public Optional<Item> findByName(String name) {
        return findByParam(name, FIND_BY_NAME_QUERY, STRING_PARAM_SETTER);
    }

    @Override
    protected Item mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Item.builder()
                .withId(resultSet.getLong("id"))
                .withName(resultSet.getString("name"))
                .withWeight(resultSet.getDouble("weight"))
                .withQuantity(resultSet.getInt("quantity"))
                .build();
    }

    @Override
    protected void prepareEntity(Item entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setDouble(2, entity.getWeight());
        preparedStatement.setInt(3, entity.getQuantity());
    }

    @Override
    protected void prepareEntityWithId(Item entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setDouble(2, entity.getWeight());
        preparedStatement.setInt(3, entity.getQuantity());
        preparedStatement.setLong(4, entity.getId());
    }
}
