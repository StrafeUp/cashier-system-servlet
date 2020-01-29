package cashiersystem.dao.impl;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectorDBTest {

    @Test
    public void shouldGetConnection() {
        try (Connection connection = ConnectorDB.getConnection()) {
            Assert.assertTrue(connection.isValid(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}