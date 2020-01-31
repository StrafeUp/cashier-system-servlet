package cashiersystem.dao.impl.crud;


import cashiersystem.dao.ReceiptCrudDao;
import cashiersystem.dao.exception.DataBaseSqlRuntimeException;
import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.entity.Item;
import cashiersystem.entity.Receipt;
import cashiersystem.entity.Role;
import cashiersystem.entity.Status;
import cashiersystem.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ReceiptPageableCrudDaoImpl extends AbstractPageableCrudDao<Receipt> implements ReceiptCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptPageableCrudDaoImpl.class);

    private static final String FIND_BY_USER_QUERY = "SELECT receipt_id, status_id, user_id, item_id, username, email, password, role_id, name, quantity, weight\n" +
            "FROM cashierSystem.receipts\n" +
            "         JOIN cashierSystem.users ON cashierSystem.receipts.user_id = cashierSystem.users.id\n" +
            "         JOIN cashierSystem.items i ON cashierSystem.receipts.item_id = i.id\n" +
            "WHERE cashierSystem.users.id = ?;";
    private static final String FIND_BY_RECEIPT_ID = "SELECT receipt_id, status_id, user_id, item_id, name, quantity, weight\n" +
            "FROM cashierSystem.receipts\n" +
            "         JOIN cashierSystem.items i on receipts.item_id = i.id\n" +
            "WHERE receipt_id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM cashierSystem.receipts WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM cashierSystem.receipts";
    private static final String FIND_ALL_QUERY_LIMIT = "SELECT * FROM cashierSystem.receipts LIMIT ?, ?";
    private static final String COUNT_ALL_ROWS = "SELECT COUNT(*) FROM cashierSystem.receipts";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM cashierSystem.receipts WHERE id =?";
    private static final String SAVE_RECEIPT_QUERY = "INSERT INTO cashierSystem.receipts (receipt_id, status_id, user_id, item_id) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_RECEIPT_QUERY = "UPDATE cashierSystem.receipts SET receipt_id=?, status_id =?, user_id=?, item_id=? WHERE id = ?";

    private static final String MAX_RECEIPT_NUMBER = "SELECT MAX(receipt_id) FROM cashierSystem.receipts";

    public ReceiptPageableCrudDaoImpl(ConnectorDB connectorDB) {
        super(FIND_BY_ID_QUERY, DELETE_BY_ID_QUERY, COUNT_ALL_ROWS,
                FIND_ALL_QUERY, FIND_ALL_QUERY_LIMIT, SAVE_RECEIPT_QUERY, UPDATE_RECEIPT_QUERY, connectorDB);
    }

    @Override
    public List<Receipt> findAllByUser(User user) {
        Long id = user.getId();

        try (PreparedStatement preparedStatement =
                     connectorDB.getConnection().prepareStatement(FIND_BY_USER_QUERY)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getReceiptListFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.error(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(FIND_BY_USER_QUERY), e);
        }
    }


    @Override
    public Receipt findByReceiptId(Long receiptId) {
        try (PreparedStatement preparedStatement =
                     connectorDB.getConnection().prepareStatement(FIND_BY_RECEIPT_ID)) {
            preparedStatement.setLong(1, receiptId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getReceiptFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.error(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(FIND_BY_USER_QUERY), e);
        }

    }

    @Override
    public void save(Receipt receipt) {
        long receiptId = getMaxReceiptNumber() + 1;
        try (PreparedStatement preparedStatement = connectorDB.getConnection().prepareStatement(SAVE_RECEIPT_QUERY)) {
            preparedStatement.setLong(1, receiptId);
            prepareEntity(receipt, preparedStatement);

            for (Item item : receipt.getItems()) {
                preparedStatement.setLong(4, item.getId());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(MAX_RECEIPT_NUMBER), e);
        }

    }

    @Override
    protected Receipt mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Receipt.builder()
                .withId(resultSet.getLong("id"))
                .withReceiptId(resultSet.getLong("receipt_id"))
                .withStatus(Status.getStatusById(resultSet.getInt("status_id")))
                .withUser(User.builder().withId(resultSet.getLong("user_id")).build())
                .build();
    }

    @Override
    protected void prepareEntity(Receipt entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(2, entity.getStatus().getId());
        preparedStatement.setLong(3, entity.getUser().getId());
    }


    @Override
    protected void prepareEntityWithId(Receipt entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, entity.getStatus().ordinal());
        preparedStatement.setLong(2, entity.getUser().getId());
        preparedStatement.setLong(3, entity.getItems().get(1).getId());
        preparedStatement.setLong(4, entity.getId());
    }

    private long getMaxReceiptNumber() {
        try (final ResultSet resultSet = connectorDB.getConnection().prepareStatement(MAX_RECEIPT_NUMBER).executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getLong("MAX(receipt_id)");
            }

        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(MAX_RECEIPT_NUMBER), e);
        }
        return 0;
    }

    private List<Receipt> getReceiptListFromResultSet(ResultSet resultSet) throws SQLException {
        resultSet.next();
        long receiptId = resultSet.getLong("receipt_id");
        List<Item> itemsList = new LinkedList<>();
        List<Receipt> receiptList = new LinkedList<>();

        Receipt.Builder receiptBuilder = Receipt.builder()
                .withReceiptId(receiptId)
                .withUser(User.builder()
                        .withId(resultSet.getLong("user_id"))
                        .withEmail(resultSet.getString("email"))
                        .withPassword(resultSet.getString("password"))
                        .withRole(Role.getRoleById(resultSet.getInt("role_id")))
                        .build())
                .withStatus(Status.getStatusById(resultSet.getInt("status_id")));

        while (resultSet.next()) {
            long receiptIdNew = resultSet.getLong("receipt_id");
            if (receiptId == receiptIdNew) {
                Item tempItem = Item.builder()
                        .withId(resultSet.getLong("item_id"))
                        .withName(resultSet.getString("name"))
                        .withWeight(resultSet.getDouble("weight"))
                        .withQuantity(resultSet.getInt("quantity")).build();
                itemsList.add(tempItem);
            } else {
                Receipt receipt = receiptBuilder.withItems(itemsList).build();
                receiptList.add(receipt);
                itemsList = Collections.emptyList();
            }
        }
        return receiptList;
    }

    private Receipt getReceiptFromResultSet(ResultSet resultSet) throws SQLException {
        List<Item> items = new LinkedList<>();
        Receipt.Builder receiptBuilder = Receipt.builder();
        long receiptId = 0;
        int statusId = 0;
        while (resultSet.next()) {
            receiptId = resultSet.getLong("receipt_id");
            statusId = resultSet.getInt("status_id");
            items.add(Item.builder()
                    .withId(resultSet.getLong("item_id"))
                    .withName(resultSet.getString("name"))
                    .withWeight(resultSet.getDouble("weight"))
                    .withQuantity(resultSet.getInt("quantity"))
                    .build());
        }

        return receiptBuilder
                .withItems(items)
                .withStatus(Status.getStatusById(statusId))
                .withReceiptId(receiptId)
                .build();
    }
}
