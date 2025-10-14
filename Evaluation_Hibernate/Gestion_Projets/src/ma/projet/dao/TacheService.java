package ma.projet.dao;

import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.util.Date;
import java.util.List;

public class TacheService {

    // ✅ Ajouter une tâche
    public void create(Tache t) {
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.save(t);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ✅ Afficher les tâches dont le prix est > 1000 DH
    public void getTachesPrixSup1000() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Tache t WHERE t.prix > 1000";
        Query query = session.createQuery(hql);
        List<Tache> taches = query.list();

        for (Tache t : taches) {
            System.out.println("Tâche : " + t.getNom() + " | Prix : " + t.getPrix());
        }
        session.close();
    }

    // ✅ Afficher les tâches réalisées entre deux dates
    public void getTachesEntreDates(Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Tache t WHERE t.dateDebutReelle BETWEEN :d1 AND :d2";
        Query query = session.createQuery(hql);
        query.setParameter("d1", d1);
        query.setParameter("d2", d2);
        List<Tache> taches = query.list();

        for (Tache t : taches) {
            System.out.println("Tâche entre dates : " + t.getNom());
        }
        session.close();
    }
}
