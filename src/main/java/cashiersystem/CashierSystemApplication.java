package cashiersystem;

import cashiersystem.dao.impl.ConnectorDB;
import cashiersystem.dao.impl.crud.ItemPageableCrudDaoImpl;
import cashiersystem.entity.Item;

import java.util.Optional;

public class CashierSystemApplication {
    public static void main(String[] args) {
        ConnectorDB connector = new ConnectorDB("database");
        ItemPageableCrudDaoImpl itemCrudDao = new ItemPageableCrudDaoImpl(connector);

        Optional<Item> kumquat = itemCrudDao.findByName("Kumquat");
        System.out.println(kumquat.get().toString());
    }
}

