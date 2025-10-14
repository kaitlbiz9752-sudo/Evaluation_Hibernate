package ma.projet.service;

import java.time.LocalDate;
import java.util.List;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

public class MariageService implements IDao<Mariage> {

    @Override
    public Mariage save(Mariage f) {
        Transaction tx = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            tx = s.beginTransaction();
            s.save(f);
            tx.commit();
            return f;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (s != null) s.close();
        }
    }

    @Override
    public Mariage update(Mariage f) {
        Transaction tx = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            tx = s.beginTransaction();
            s.update(f);
            tx.commit();
            return f;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (s != null) s.close();
        }
    }

    @Override
    public void delete(Object id) {
        Transaction tx = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            tx = s.beginTransaction();
            Mariage f = (Mariage) s.get(Mariage.class, (Integer) id);
            if (f != null) {
                s.delete(f);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (s != null) s.close();
        }
    }

    @Override
    public Mariage findById(Object id) {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            return (Mariage) s.get(Mariage.class, (Integer) id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (s != null) s.close();
        }
    }

    @Override
public List<Mariage> findAll() {
    Session s = null;
    try {
        s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("from Mariage");        // non-générique, compatible
        @SuppressWarnings("unchecked")
        List<Mariage> list = (List<Mariage>) q.list();    // list() fonctionne dans l'API Hibernate
        return list;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        if (s != null) s.close();
    }
}

    // ✅ Nombre d’enfants entre deux dates
   public int getNombreEnfantsBetween(Integer idMariage, LocalDate dateDebut, LocalDate dateFin) {
    Session s = null;
    try {
        s = HibernateUtil.getSessionFactory().openSession();
        // Requête non-générique (compatible avec les anciennes versions)
        Query q = s.createQuery(
            "SELECT SUM(m.nbrEnfant) FROM Mariage m " +
            "WHERE m.femme.id = :idf AND m.dateDebut BETWEEN :d1 AND :d2"
        );
        q.setParameter("idf", idMariage);
        q.setParameter("d1", dateDebut);
        q.setParameter("d2", dateFin);

        Object res = q.uniqueResult(); // peut être Long, BigDecimal, Integer ou null selon le driver/version
        if (res == null) return 0;

        // Convertir prudemment en int
        if (res instanceof Number) {
            return ((Number) res).intValue();
        } else {
            try {
                return Integer.parseInt(res.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                return 0;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    } finally {
        if (s != null) s.close();
    }
}

    // ✅ Mariages mariées deux fois ou plus
   public List<Mariage> getMariagesMarieesDeuxFoisOuPlus() {
    Session s = null;
    try {
        s = HibernateUtil.getSessionFactory().openSession();

        // ✅ Version compatible avec Hibernate 4.x et NetBeans 8.0.2
        Query q = s.createQuery(
            "SELECT f FROM Mariage f WHERE " +
            "(SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
        );

        @SuppressWarnings("unchecked")
        List<Mariage> list = (List<Mariage>) q.list();
        return list;

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        if (s != null) s.close();
    }
}
}
