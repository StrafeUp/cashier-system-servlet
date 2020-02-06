package cashiersystem.service;

import cashiersystem.dao.domain.Page;
import java.util.List;

public interface PageableService<T> {

    List<T> findAll(Page page);

    int count();
}
