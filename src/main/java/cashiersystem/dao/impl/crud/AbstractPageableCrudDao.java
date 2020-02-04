package cashiersystem.dao.impl.crud;

import cashiersystem.dao.ConnectionPool;
import cashiersystem.dao.CrudPageableDao;
import cashiersystem.dao.domain.Page;
import cashiersystem.dao.exception.DataBaseSqlRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class AbstractPageableCrudDao<E> implements CrudPageableDao<E> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractPageableCrudDao.class);
    protected static final String ERROR_TEMPLATE = "Can't execute query ['%s'];";
    protected static final BiConsumer<PreparedStatement, String> STRING_PARAM_SETTER = ((preparedStatement, string) -> {
        try {
            preparedStatement.setString(1, string);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    });
    private static final BiConsumer<PreparedStatement, Integer> INT_PARAM_SETTER = ((preparedStatement, integer) -> {
        try {
            preparedStatement.setInt(1, integer);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    });
    protected final ConnectionPool connectionPool;
    private final String findByIdQuery;
    private final String deleteByIdQuery;
    private final String countQuery;
    private final String findAllQuery;
    private final String findAllLimitQuery;
    private final String saveEntityQuery;
    private final String updateItemQuery;


    protected AbstractPageableCrudDao(String findByIdQuery, String deleteByIdQuery, String countQuery,
                                      String findAllQuery, String findAllLimitQuery, String saveEntityQuery,
                                      String updateItemQuery, ConnectionPool connectionPool) {
        this.findByIdQuery = findByIdQuery;
        this.deleteByIdQuery = deleteByIdQuery;
        this.countQuery = countQuery;
        this.findAllQuery = findAllQuery;
        this.findAllLimitQuery = findAllLimitQuery;
        this.saveEntityQuery = saveEntityQuery;
        this.updateItemQuery = updateItemQuery;
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<E> findById(Integer id) {
        return findByParam(id, findByIdQuery, INT_PARAM_SETTER);
    }

    @Override
    public long count() {
        try (final ResultSet resultSet = connectionPool.getConnection().prepareStatement(countQuery).executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(countQuery), e);
        }
        return 0;
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(deleteByIdQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(deleteByIdQuery), e);
        }
    }

    @Override
    public List<E> findAll() {
        try (final ResultSet resultSet = connectionPool.getConnection().prepareStatement(findAllQuery).executeQuery()) {
            List<E> tempList = new ArrayList<>();
            while (resultSet.next()) {
                final E optionalEntity = mapResultSetToEntity(resultSet);
                tempList.add(optionalEntity);
            }
            return tempList;
        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(findAllQuery), e);
        }
    }

    @Override
    public List<E> findAll(Page page) {
        try (final PreparedStatement preparedStatement =
                     connectionPool.getConnection().prepareStatement(findAllLimitQuery)) {
            preparedStatement.setInt(1, (page.getPageNumber() - 1) * page.getItemsPerPage());
            preparedStatement.setInt(2, page.getItemsPerPage());

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<E> items = new ArrayList<>();
                while (resultSet.next()) {
                    final E optionalEntity = mapResultSetToEntity(resultSet);
                    items.add(optionalEntity);
                }
                return items;
            }
        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(findAllLimitQuery), e);
        }
    }

    @Override
    public void save(E entity) {
        try (PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(saveEntityQuery)) {
            prepareEntity(entity, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(saveEntityQuery), e);
        }
    }

    @Override
    public void update(E entity) {
        try (PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(updateItemQuery)) {
            prepareEntityWithId(entity, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(saveEntityQuery), e);
        }
    }

    protected <P> Optional<E> findByParam(P param, String findByParam, BiConsumer<PreparedStatement, P> designatedParamSetter) {
        try (PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(findByParam)) {
            designatedParamSetter.accept(preparedStatement, param);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(prepareErrorMessage(e.getSQLState()));
            throw new DataBaseSqlRuntimeException(prepareErrorMessage(findByParam), e);
        }
        return Optional.empty();
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void prepareEntity(E entity, PreparedStatement preparedStatement) throws SQLException;

    protected abstract void prepareEntityWithId(E entity, PreparedStatement preparedStatement) throws SQLException;

    protected String prepareErrorMessage(String query) {
        return String.format(ERROR_TEMPLATE, query);
    }
}