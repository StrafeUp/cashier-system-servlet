package cashiersystem.dao.impl;

import cashiersystem.dao.ConnectionPool;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HikariCPManager implements ConnectionPool {
    private final HikariDataSource dataSource;

    public HikariCPManager(String filename) {
        ResourceBundle resource = ResourceBundle.getBundle(filename);
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(resource.getString("db.driver"));
        config.setJdbcUrl(resource.getString("db.url"));
        config.setUsername(resource.getString("db.user"));
        config.setPassword(resource.getString("db.password"));
        config.setMaximumPoolSize(getIntProperty(resource,"db.pool.size"));
        config.setConnectionTimeout(getIntProperty(resource, "db.timeout"));
        this.dataSource = new HikariDataSource(config);
    }

    private int getIntProperty(ResourceBundle resource, String key) {
        return Integer.parseInt(resource.getString(key));
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeConnection() {
        dataSource.close();
    }
}
