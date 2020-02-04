package cashiersystem.dao.impl;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class HikariCPManagerTest {

    private static final String H2 = "h2db";

    @Test
    public void testConfigurationH2() {
        HikariCPManager connector = new HikariCPManager(H2);
        assertNotNull(connector);
        try {
            assertNotNull(connector.getConnection());
        } catch (SQLException e) {
            fail();
        }
    }
}