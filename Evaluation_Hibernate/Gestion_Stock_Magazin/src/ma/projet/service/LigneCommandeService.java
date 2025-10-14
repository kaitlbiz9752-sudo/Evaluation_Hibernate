/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;
import java.util.List;
import ma.projet.classes.ligne_commande_produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author Lenovo
 */
public class LigneCommandeService implements IDao<ligne_commande_produit, Integer> {

    @Override
    public ligne_commande_produit save(ligne_commande_produit lcp) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(lcp);
            tx.commit();
            return lcp;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public ligne_commande_produit update(ligne_commande_produit lcp) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(lcp);
            tx.commit();
            return lcp;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void delete(ligne_commande_produit lcp) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(lcp);
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
        ligne_commande_produit lcp = findById(id);
        if (lcp != null) delete(lcp);
    }

    @Override
    public ligne_commande_produit findById(Integer id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // adapte la casse si ta classe d'entité a une autre casse (LigneCommandeProduit)
            return (ligne_commande_produit) session.get(ma.projet.classes.ligne_commande_produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<ligne_commande_produit> findAll() {
        Session session = null;
        List<ligne_commande_produit> lignes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            lignes = (List<ligne_commande_produit>) session.createQuery("from ligne_commande_produit").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lignes;
    }
}