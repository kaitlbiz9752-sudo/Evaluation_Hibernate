package ma.projet.dao;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.util.List;

public class ProjetService {

    // ✅ Ajouter un projet
    public void create(Projet p) {
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.save(p);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ✅ Afficher la liste des tâches planifiées pour un projet
    public void getTachesPlanifiees(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT t FROM Tache t WHERE t.projet.id = :projId";
        Query query = session.createQuery(hql);
        query.setParameter("projId", projetId);
        List<Tache> taches = query.list();

        for (Tache t : taches) {
            System.out.println("Tâche planifiée : " + t.getNom() + " - Date début : " + t.getDateDebut());
        }
        session.close();
    }

    // ✅ Afficher la liste des tâches réalisées avec les dates réelles
    public void getTachesRealisees(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT DISTINCT t FROM Tache t JOIN t.employeTaches et WHERE t.projet.id = :projId";
        Query query = session.createQuery(hql);
        query.setParameter("projId", projetId);
        List<Tache> taches = query.list();

        for (Tache t : taches) {
            System.out.println("Tâche : " + t.getNom() +
                    " | Début réelle : " + t.getDateDebutReelle() +
                    " | Fin réelle : " + t.getDateFinReelle());
        }
        session.close();
    }
}
