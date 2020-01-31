package cashiersystem.dao;

import cashiersystem.dao.domain.Page;
import java.util.List;

public interface CrudPageableDao<E> extends CrudDao<E> {

    List<E> findAll(Page page);

    long count();
}
