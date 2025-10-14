/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.dao;

import java.io.Serializable;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public interface IDao<T, ID extends Serializable> {

    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(ID id);

    T findById(ID id);

    List<T> findAll();
}
