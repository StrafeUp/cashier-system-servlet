package cashiersystem.dao.impl.crud;

import cashiersystem.dao.ConnectionPool;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.fail;

public abstract class AbstractEntityCrudDaoImplTest {

    public static final String SCHEMA_PATH = "src/test/resources/schema.sql";
    public static final String DATA_PATH = "src/test/resources/data.sql";

    public void initTestDb(ConnectionPool connector) {
        try {
            Connection connection = connector.getConnection();
            final Statement executeStatement = connection.createStatement();
            String schemaQuery = new String(Files.readAllBytes(Paths.get(SCHEMA_PATH)));
            executeStatement.execute(schemaQuery);

            String dataQuery = new String(Files.readAllBytes(Paths.get(DATA_PATH)));
            executeStatement.execute(dataQuery);

            executeStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
