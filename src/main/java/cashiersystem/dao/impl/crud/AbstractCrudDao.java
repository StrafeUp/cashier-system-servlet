package cashiersystem.dao.impl.crud;

import cashiersystem.dao.CrudDao;
import cashiersystem.dao.exception.DataBaseSqlRuntimeException;
import cashiersystem.dao.impl.ConnectorDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class AbstractCrudDao<E> implements CrudDao<E> {
    protected static final String LOGGER_ERROR = "Can't execute query ['%s'];";
    private static final Logger LOGGER = LogManager.getLogger(AbstractCrudDao.class);
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

    private final String findByIdQuery;
    private final String deleteByIdQuery;

    protected AbstractCrudDao(String findByIdQuery, String deleteByIdQuery) {
        this.findByIdQuery = findByIdQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    @Override
    public Optional<E> findById(Integer id) {
        return findByParam(id, findByIdQuery, INT_PARAM_SETTER);
    }

    protected <P> Optional<E> findByParam(P param, String findByParam, BiConsumer<PreparedStatement, P> designatedParamSetter) {
        try (PreparedStatement preparedStatement = ConnectorDB.getConnection().prepareStatement(findByParam)) {
            designatedParamSetter.accept(preparedStatement, param);
            try (ResultSet resultSet = preparedStatement.executeQuery(findByParam)) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR, e));
            throw new DataBaseSqlRuntimeException("Can't obtain connection", e);
        }
        return Optional.empty();
    }


    public long count(String query) {
        try (final PreparedStatement preparedStatement = ConnectorDB.getConnection().prepareStatement(query)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR, e));
            throw new DataBaseSqlRuntimeException("", e);
        }
        return 0;
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement preparedStatement = ConnectorDB.getConnection().prepareStatement(deleteByIdQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(String.format(LOGGER_ERROR, e));
            throw new DataBaseSqlRuntimeException("", e);
        }
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}