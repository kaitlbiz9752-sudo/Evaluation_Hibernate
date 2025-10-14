/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;
import ma.projet.classes.produit;
import ma.projet.classes.categorie;
import ma.projet.classes.commande;
import ma.projet.classes.ligne_commande_produit;
import ma.projet.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.Date;
import java.util.List;
import ma.projet.dao.IDao;
/**
 *
 * @author Lenovo
 */
public class ProduitService implements IDao<produit, Integer> {

    @Override
    public produit save(produit p) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(p);
            tx.commit();
            return p;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public produit update(produit p) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(p);
            tx.commit();
            return p;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void delete(produit p) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(p);
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
        produit p = findById(id);
        if (p != null) delete(p);
    }

    @Override
    public produit findById(Integer id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // Hibernate 4 : nom complet de la classe et cast
            return (produit) session.get("ma.projet.classes.produit", id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<produit> findAll() {
        Session session = null;
        List<produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produits = (List<produit>) session.createQuery("from produit").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produits;
    }

    // Afficher la liste des produits par catégorie
    public List<produit> findByCategorie(int categorieId) {
        Session session = null;
        List<produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery(
                "from produit p where p.categorie.id = :cid"
            );
            query.setParameter("cid", categorieId);
            produits = (List<produit>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produits;
    }

    // Afficher les produits commandés entre deux dates
    public List<ligne_commande_produit> findProductsBetweenDates(Date d1, Date d2) {
        Session session = null;
        List<ligne_commande_produit> lignes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery(
                "from ligne_commande_produit lc where lc.commande.dateCommande between :d1 and :d2"
            );
            query.setParameter("d1", d1);
            query.setParameter("d2", d2);
            lignes = (List<ligne_commande_produit>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lignes;
    }

    // Afficher les produits d’une commande donnée
    public List<ligne_commande_produit> findProductsByCommande(int commandeId) {
        Session session = null;
        List<ligne_commande_produit> lignes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery(
                "from ligne_commande_produit lc where lc.commande.id = :cid"
            );
            query.setParameter("cid", commandeId);
            lignes = (List<ligne_commande_produit>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lignes;
    }

    // Afficher les produits dont le prix > 100 DH (requête nommée)
    public List<produit> findProductsPriceGreaterThan100() {
        Session session = null;
        List<produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.getNamedQuery("produit.findByPriceGreaterThan100");
            produits = (List<produit>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produits;
    }
}