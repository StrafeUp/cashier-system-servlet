package cashiersystem.dao.impl;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class ConnectorDBTest {

    private static final String MYSQL = "database";
    private static final String H2 = "h2db";

    @Test
    public void testConfiguration() {
        ConnectorDB connector = new ConnectorDB(MYSQL);
        assertNotNull(connector);
        try {
            assertNotNull(connector.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testConfigurationH2() {
        ConnectorDB connector = new ConnectorDB(H2);
        assertNotNull(connector);
        try {
            assertNotNull(connector.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}