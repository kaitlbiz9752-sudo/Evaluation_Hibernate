package ma.projet.dao;

import java.util.List;

public interface IDao<T> {
    T save(T t);
    T update(T t);
    void delete(Object id);
    T findById(Object id);
    List<T> findAll();
}
