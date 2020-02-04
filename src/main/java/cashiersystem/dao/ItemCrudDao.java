package cashiersystem.dao;

import cashiersystem.entity.ItemEntity;
import java.util.Optional;

public interface ItemCrudDao extends CrudPageableDao<ItemEntity>{

    Optional<ItemEntity> findByName(String name);
}
