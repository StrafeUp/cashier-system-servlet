package cashiersystem.dao.impl.crud;


import cashiersystem.dao.ConnectionPool;
import cashiersystem.dao.ReceiptCrudDao;
import cashiersystem.dao.exception.DataBaseSqlRuntimeException;
import cashiersystem.dao.exception.NotEnoughItemsException;
import cashiersystem.entity.ItemEntity;
import cashiersystem.entity.ReceiptEntity;
import cashiersystem.entity.Role;
import cashiersystem.entity.Status;
import cashiersystem.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReceiptPageableCrudDaoImpl extends AbstractPageableCrudDao<ReceiptEntity> implements ReceiptCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptPageableCrudDaoImpl.class);

    private static final String FIND_BY_USER_QUERY = "SELECT receipt_id, status_id, user_id, item_id, username, email, password, role_id, name, quantity, weight\n" +
            "FROM receipts\n" +
            "         JOIN users ON receipts.user_id = users.id\n" +
            "         JOIN items i ON receipts.item_id = i.id\n" +
            "WHERE users.id = ?;";
    private static final String FIND_BY_RECEIPT_ID = "SELECT receipt_id, status_id, user_id, item_id, name, quantity, weight\n" +
            "FROM receipts\n" +
            "         JOIN items i on receipts.item_id = i.id\n" +
            "WHERE receipt_id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM receipts WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM receipts";
    private static final String FIND_ALL_QUERY_LIMIT = "SELECT * FROM receipts LIMIT ?, ?";
    private static final String COUNT_ALL_ROWS = "SELECT COUNT(*) FROM receipts";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM receipts WHERE id =?";
    private static final String SAVE_RECEIPT_QUERY = "INSERT INTO receipts (receipt_id, status_id, user_id, item_id) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_RECEIPT_QUERY = "UPDATE receipts SET receipt_id=?, status_id =?, user_id=?, item_id=? WHERE id = ?";
    private static final String MAX_RECEIPT_NUMBER = "SELECT MAX(receipt_id) FROM receipts";


    public ReceiptPageableCrudDaoImpl(ConnectionPool connectionPool) {
        super(FIND_BY_ID_QUERY, DELETE_BY_ID_QUERY, COUNT_ALL_ROWS,
                FIND_ALL_QUERY, FIND_ALL_QUERY_LIMIT, SAVE_RECEIPT_QUERY, UPDATE_RECEIPT_QUERY, connectionPool);
    }

    @Override
    public List<ReceiptEntity> findAllByUser(UserEntity userEntity) {
        Long id = userEntity.getId();

        try (PreparedStatement preparedStatement =
                     connectionPool.getConnection().prepareStatement(FIND_BY_USER_QUERY)) {
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
    public ReceiptEntity findByReceiptId(Long receiptId) {
        try (PreparedStatement preparedStatement =
                     connectionPool.getConnection().prepareStatement(FIND_BY_RECEIPT_ID)) {
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
    public void save(ReceiptEntity receiptEntity) {
        Connection connection0 = null;
        try (Connection connection = connectionPool.getConnection()) {
            connection0 = connection;
            connection.setAutoCommit(false);

            ItemPageableCrudDaoImpl itemPageableCrudDao = new ItemPageableCrudDaoImpl(connectionPool);
            Map<String, Integer> itemToQuantity = itemPageableCrudDao.getItemToQuantity();

            for (ItemEntity itemEntity : receiptEntity.getItemEntities()) {
                if (itemToQuantity.get(itemEntity.getName()) - itemEntity.getQuantity() < 0) {
                    throw new NotEnoughItemsException("Not enough items in the vault" + itemEntity.getName());
                }

                ItemEntity newItemEntity = ItemEntity.builder()
                        .withId(itemEntity.getId())
                        .withName(itemEntity.getName())
                        .withWeight(itemEntity.getWeight())
                        .withQuantity(itemToQuantity.get(itemEntity.getName()) - itemEntity.getQuantity())
                        .build();

                itemPageableCrudDao.update(newItemEntity); //TODO custom query
            }
            long receiptId = getMaxReceiptNumber() + 1;

            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_RECEIPT_QUERY);
            preparedStatement.setLong(1, receiptId);
            prepareEntity(receiptEntity, preparedStatement);

            for (ItemEntity itemEntity : receiptEntity.getItemEntities()) {
                preparedStatement.setLong(4, itemEntity.getId());
                preparedStatement.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            try {
                connection0.rollback();
            } catch (SQLException ex) {
                LOGGER.warn(prepareErrorMessage(e.getSQLState()));
                throw new DataBaseSqlRuntimeException(prepareErrorMessage(SAVE_RECEIPT_QUERY), e);
            }
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(SAVE_RECEIPT_QUERY), e);
        }
    }

    @Override
    protected ReceiptEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return ReceiptEntity.builder()
                .withId(resultSet.getLong("id"))
                .withReceiptId(resultSet.getLong("receipt_id"))
                .withStatus(Status.getStatusById(resultSet.getInt("status_id")))
                .withUser(UserEntity.builder().withId(resultSet.getLong("user_id")).build())
                .build();
    }

    @Override
    protected void prepareEntity(ReceiptEntity entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(2, entity.getStatus().getId());
        preparedStatement.setLong(3, entity.getUserEntity().getId());
    }


    @Override
    protected void prepareEntityWithId(ReceiptEntity entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, entity.getStatus().ordinal());
        preparedStatement.setLong(2, entity.getUserEntity().getId());
        preparedStatement.setLong(3, entity.getItemEntities().get(1).getId());
        preparedStatement.setLong(4, entity.getId());
    }

    private long getMaxReceiptNumber() {
        try (final ResultSet resultSet = connectionPool.getConnection().prepareStatement(MAX_RECEIPT_NUMBER).executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getLong("MAX(receipt_id)");
            }

        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(MAX_RECEIPT_NUMBER), e);
        }
        return 0;
    }

    private List<ReceiptEntity> getReceiptListFromResultSet(ResultSet resultSet) throws SQLException {
        resultSet.next();
        long receiptId = resultSet.getLong("receipt_id");
        List<ItemEntity> itemsList = new LinkedList<>();
        List<ReceiptEntity> receiptEntityList = new LinkedList<>();

        ReceiptEntity.Builder receiptBuilder = ReceiptEntity.builder()
                .withReceiptId(receiptId)
                .withUser(UserEntity.builder()
                        .withId(resultSet.getLong("user_id"))
                        .withEmail(resultSet.getString("email"))
                        .withPassword(resultSet.getString("password"))
                        .withRole(Role.getRoleById(resultSet.getInt("role_id")))
                        .build())
                .withStatus(Status.getStatusById(resultSet.getInt("status_id")));

        while (resultSet.next()) {
            long receiptIdNew = resultSet.getLong("receipt_id");
            if (receiptId == receiptIdNew) {
                ItemEntity tempItemEntity = ItemEntity.builder()
                        .withId(resultSet.getLong("item_id"))
                        .withName(resultSet.getString("name"))
                        .withWeight(resultSet.getDouble("weight"))
                        .withQuantity(resultSet.getInt("quantity")).build();
                itemsList.add(tempItemEntity);
            } else {
                ReceiptEntity receiptEntity = receiptBuilder.withItems(itemsList).build();
                receiptEntityList.add(receiptEntity);
                itemsList = Collections.emptyList();
            }
        }
        return receiptEntityList;
    }

    private ReceiptEntity getReceiptFromResultSet(ResultSet resultSet) throws SQLException {
        List<ItemEntity> itemEntities = new LinkedList<>();
        ReceiptEntity.Builder receiptBuilder = ReceiptEntity.builder();
        long receiptId = 0;
        int statusId = 0;
        while (resultSet.next()) {
            receiptId = resultSet.getLong("receipt_id");
            statusId = resultSet.getInt("status_id");
            itemEntities.add(ItemEntity.builder()
                    .withId(resultSet.getLong("item_id"))
                    .withName(resultSet.getString("name"))
                    .withWeight(resultSet.getDouble("weight"))
                    .withQuantity(resultSet.getInt("quantity"))
                    .build());
        }

        return receiptBuilder
                .withItems(itemEntities)
                .withStatus(Status.getStatusById(statusId))
                .withReceiptId(receiptId)
                .build();
    }
}
