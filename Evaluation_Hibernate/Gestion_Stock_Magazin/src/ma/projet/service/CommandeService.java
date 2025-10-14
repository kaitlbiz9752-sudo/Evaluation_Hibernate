/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;
import java.util.List;
import ma.projet.classes.commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author Lenovo
 */
public class CommandeService implements IDao<commande, Integer> {

    @Override
    public commande save(commande c) {
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
    public commande update(commande c) {
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
    public void delete(commande c) {
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
        commande c = findById(id);
        if (c != null) delete(c);
    }

    @Override
  public commande findById(Integer id) {
    org.hibernate.Session session = null;
    try {
        session = ma.projet.util.HibernateUtil.getSessionFactory().openSession();
        return (commande) session.get(ma.projet.classes.commande.class, id); // <-- Commande avec majuscule
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        if (session != null) session.close();
    }
}

    @Override
    public List<commande> findAll() {
        Session session = null;
        List<commande> commandes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            commandes = (List<commande>) session.createQuery("from commande").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return commandes;
    }
}
