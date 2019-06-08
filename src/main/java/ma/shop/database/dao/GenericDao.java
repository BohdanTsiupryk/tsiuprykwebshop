package ma.shop.database.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    boolean add(T object);
    boolean deleteById(long id);
    Optional<T> getById(long id);
    List<T> getAll();
    boolean update(T object);
}
