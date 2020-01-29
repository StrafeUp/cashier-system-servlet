package cashiersystem.dao.impl.crud;


import cashiersystem.dao.ReceiptCrudDao;
import cashiersystem.dao.exception.DataBaseSqlRuntimeException;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Receipt;
import cashiersystem.entity.Status;
import cashiersystem.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptCrudDaoImpl extends AbstractCrudDao<Receipt> implements ReceiptCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptCrudDaoImpl.class);

    private static final String FIND_BY_USER_QUERY = "SELECT * FROM cashierSystem.receipts WHERE user_id=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM cashierSystem.receipts WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM cashierSystem.receipts";
    private static final String FIND_ALL_QUERY_LIMIT = "SELECT * FROM cashierSystem.receipts LIMIT ?, ?";
    private static final String COUNT_ALL_ROWS = "SELECT COUNT(*) FROM cashierSystem.receipts";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM cashierSystem.receipts WHERE id =?";
    private static final String SAVE_RECEIPT_QUERY = "INSERT INTO cashierSystem.receipts (status_id, user_id, item_id) VALUES (?, ?, ?);";
    private static final String UPDATE_RECEIPT_QUERY = "UPDATE cashierSystem.receipts SET status_id =?, user_id=?, item_id=? WHERE id = ?";

    protected ReceiptCrudDaoImpl() {
        super(FIND_BY_ID_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    public List<Receipt> findAllByUser(User user) {
        try (final PreparedStatement preparedStatement =
                     ConnectorDB.getConnection().prepareStatement(FIND_BY_USER_QUERY)) {
            preparedStatement.setLong(1, user.getId());

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Receipt> receiptsByUser = new ArrayList<>();
                while (resultSet.next()) {
                    final Receipt optionalUser = mapResultSetToEntity(resultSet);
                    receiptsByUser.add(optionalUser);
                }
                return receiptsByUser;
            }
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR,e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }

    @Override
    public List<Receipt> findAll(int page, int itemsPerPage) {
        try (final PreparedStatement preparedStatement =
                     ConnectorDB.getConnection().prepareStatement(FIND_ALL_QUERY_LIMIT)) {
            preparedStatement.setInt(1, (page - 1) * itemsPerPage);
            preparedStatement.setInt(2, itemsPerPage);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Receipt> receipts = new ArrayList<>();
                while (resultSet.next()) {
                    final Receipt optionalUser = mapResultSetToEntity(resultSet);
                    receipts.add(optionalUser);
                }
                return receipts;
            }

        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR,e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }

    @Override
    public long count() {
        return super.count(COUNT_ALL_ROWS);
    }

    @Override
    protected Receipt mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Receipt.builder()
                .withId(resultSet.getLong("id"))
                .withStatus(Status.valueOf(resultSet.getString("status_id")))
                .withUserId(resultSet.getInt("user_id"))
                .build();
    }


    @Override
    public void save(Receipt entity) {
        try (final PreparedStatement preparedStatement = ConnectorDB.getConnection().prepareStatement(SAVE_RECEIPT_QUERY)) {
            preparedStatement.setInt(1, entity.getStatus().ordinal());
            preparedStatement.setLong(2, entity.getUserId());
            preparedStatement.setLong(3, entity.getItems().get(1).getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR,e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }

    @Override
    public List<Receipt> findAll() {
        try (final PreparedStatement preparedStatement =
                     ConnectorDB.getConnection().prepareStatement(FIND_ALL_QUERY)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Receipt> receipts = new ArrayList<>();
                while (resultSet.next()) {
                    final Receipt optionalUser = mapResultSetToEntity(resultSet);
                    receipts.add(optionalUser);
                }
                return receipts;
            }

        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR,e));
            throw new DataBaseSqlRuntimeException("", e);
        }

    }

    @Override
    public void update(Receipt entity) {
        try (final PreparedStatement preparedStatement = ConnectorDB.getConnection().prepareStatement(UPDATE_RECEIPT_QUERY)) {
            preparedStatement.setInt(1, entity.getStatus().ordinal());
            preparedStatement.setLong(2, entity.getUserId());
            preparedStatement.setLong(3, entity.getItems().get(1).getId());
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR,e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }
}
