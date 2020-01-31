package cashiersystem.dao;

import cashiersystem.entity.Item;
import java.util.Optional;

public interface ItemCrudDao extends CrudPageableDao<Item>{

    Optional<Item> findByName(String name);
}
