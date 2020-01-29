package cashiersystem.dao.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorDB {
    private static final Logger LOGGER = LogManager.getLogger(ConnectorDB.class);
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    static {

        FileInputStream fis = null;
        Properties databaseProperties = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/database.properties");
            databaseProperties.load(fis);

        } catch (IOException e) {
            LOGGER.error(e);
        }

        config.setJdbcUrl(databaseProperties.getProperty("db.url"));
        config.setUsername(databaseProperties.getProperty("db.user"));
        config.setPassword(databaseProperties.getProperty("db.password"));

        dataSource = new HikariDataSource(config);
    }

    private ConnectorDB() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
