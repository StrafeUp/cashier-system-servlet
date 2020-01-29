package cashiersystem.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E> {

    void save(E entity);

    Optional<E> findById(Integer id);

    List<E> findAll();

    void update(E entity);

    void deleteById(Integer id);

    long count(String query);
}
