package ma.projet.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao<T, K extends Serializable> {
    T create(T t);
    T update(T t);
    void delete(T t);
    T findById(K id);
    List<T> findAll();
}
