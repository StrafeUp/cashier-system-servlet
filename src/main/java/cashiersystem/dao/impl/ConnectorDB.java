package cashiersystem.dao.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectorDB {
    private HikariDataSource dataSource;

    public ConnectorDB(String filename) {
        ResourceBundle resource = ResourceBundle.getBundle(filename);
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(resource.getString("db.driver"));
        config.setJdbcUrl(resource.getString("db.url"));
        config.setUsername(resource.getString("db.user"));
        config.setPassword(resource.getString("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(resource.getString("db.pool.size")));
        config.setConnectionTimeout(Long.parseLong(resource.getString("db.timeout")));
        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeConnection() {
        dataSource.close();
    }
}
