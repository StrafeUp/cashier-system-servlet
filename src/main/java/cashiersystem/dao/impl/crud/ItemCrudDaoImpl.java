package cashiersystem.dao.impl.crud;

import cashiersystem.dao.ItemCrudDao;
import cashiersystem.dao.exception.DataBaseSqlRuntimeException;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemCrudDaoImpl extends AbstractCrudDao<Item> implements ItemCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(ItemCrudDaoImpl.class);

    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM cashierSystem.items WHERE name=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM cashierSystem.items WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM cashierSystem.items";
    private static final String FIND_ALL_QUERY_LIMIT = "SELECT * FROM cashierSystem.items LIMIT ?, ?";
    private static final String COUNT_ALL_ROWS = "SELECT COUNT(*) FROM cashierSystem.items";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM cashierSystem.items WHERE id =";
    private static final String SAVE_ITEM_QUERY = "INSERT INTO cashierSystem.items (name, weight, quantity) values (?,?,?);";
    private static final String UPDATE_ITEM_QUERY = "UPDATE cashierSystem.items SET name =?, weight=?, quantity=? WHERE id = ?";

    protected ItemCrudDaoImpl() {
        super(FIND_BY_ID_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    public Optional<Item> findByName(String name) {
        return findByParam(name, FIND_BY_NAME_QUERY, STRING_PARAM_SETTER);
    }

    @Override
    public void save(Item entity) {
        try (final PreparedStatement preparedStatement = ConnectorDB.getConnection().prepareStatement(SAVE_ITEM_QUERY)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getWeight());
            preparedStatement.setInt(3, entity.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR, e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }

    @Override
    public List<Item> findAll() {
        try (final PreparedStatement preparedStatement =
                     ConnectorDB.getConnection().prepareStatement(FIND_ALL_QUERY)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    final Item optionalUser = mapResultSetToEntity(resultSet);
                    items.add(optionalUser);
                }
                return items;
            }

        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR, e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }

    @Override
    public List<Item> findAll(int page, int itemsPerPage) {
        try (final PreparedStatement preparedStatement =
                     ConnectorDB.getConnection().prepareStatement(FIND_ALL_QUERY_LIMIT)) {
            preparedStatement.setInt(1, (page - 1) * itemsPerPage);
            preparedStatement.setInt(2, itemsPerPage);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    final Item optionalUser = mapResultSetToEntity(resultSet);
                    items.add(optionalUser);
                }
                return items;
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
    protected Item mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Item.builder()
                .withId(resultSet.getLong("id"))
                .withName(resultSet.getString("name"))
                .withWeight(resultSet.getDouble("weight"))
                .withQuantity(resultSet.getInt("quantity"))
                .build();
    }

    @Override
    public void update(Item entity) {
        try (final PreparedStatement preparedStatement = ConnectorDB.getConnection().prepareStatement(UPDATE_ITEM_QUERY)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getWeight());
            preparedStatement.setInt(3, entity.getQuantity());
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR,e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }
}
