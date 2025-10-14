/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;


import java.util.List;
import ma.projet.classes.categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author Lenovo
 */
public class CategorieService implements IDao<categorie, Integer> {

    @Override
    public categorie save(categorie c) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
            return c;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public categorie update(categorie c) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(c);
            tx.commit();
            return c;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void delete(categorie c) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(c);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void deleteById(Integer id) {
        categorie c = findById(id);
        if (c != null) delete(c);
    }

    @Override
  public categorie findById(Integer id) {
    org.hibernate.Session session = null;
    try {
        session = ma.projet.util.HibernateUtil.getSessionFactory().openSession();
       
        return (categorie) session.get(ma.projet.classes.categorie.class, id);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        if (session != null) session.close();
    }
}

    @Override
   public List<categorie> findAll() {
    Session session = null;
    List<categorie> categories = null;
    try {
        session = HibernateUtil.getSessionFactory().openSession();
        categories = session.createQuery("from categorie").list();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (session != null) session.close();
    }
    return categories;
}
}
