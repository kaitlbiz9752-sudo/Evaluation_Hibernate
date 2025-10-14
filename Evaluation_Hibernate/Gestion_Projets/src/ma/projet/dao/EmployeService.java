package ma.projet.dao;

import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import ma.projet.classes.Projet;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.util.List;

public class EmployeService {

    // ✅ Méthode pour ajouter un employé
    public void create(Employe e) {
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ✅ Afficher la liste des tâches réalisées par un employé
    public void getTachesByEmploye(int employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT t FROM Tache t JOIN t.employeTaches et WHERE et.employe.id = :empId";
        Query query = session.createQuery(hql);
        query.setParameter("empId", employeId);
        List<Tache> taches = query.list();

        for (Tache t : taches) {
            System.out.println("Tâche : " + t.getNom());
        }
        session.close();
    }

    // ✅ Afficher la liste des projets gérés par un employé
    public void getProjetsByEmploye(int employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT DISTINCT p FROM Projet p JOIN p.taches t JOIN t.employeTaches et WHERE et.employe.id = :empId";
        Query query = session.createQuery(hql);
        query.setParameter("empId", employeId);
        List<Projet> projets = query.list();

        for (Projet p : projets) {
            System.out.println("Projet : " + p.getNom());
        }
        session.close();
    }
}
