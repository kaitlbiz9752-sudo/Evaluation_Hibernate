package ma.projet.service;

import java.time.LocalDate;
import java.util.List;
import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

public class FemmeService implements IDao<Femme> {

    @Override
    public Femme save(Femme f) {
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
    public Femme update(Femme f) {
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
            Femme f = (Femme) s.get(Femme.class, (Integer) id);
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
    public Femme findById(Object id) {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            return (Femme) s.get(Femme.class, (Integer) id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (s != null) s.close();
        }
    }

    @Override
public List<Femme> findAll() {
    Session s = null;
    try {
        s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("from Femme");        // non-générique, compatible
        @SuppressWarnings("unchecked")
        List<Femme> list = (List<Femme>) q.list();    // list() fonctionne dans l'API Hibernate
        return list;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        if (s != null) s.close();
    }
}

    // ✅ Nombre d’enfants entre deux dates
   public int getNombreEnfantsBetween(Integer idFemme, LocalDate dateDebut, LocalDate dateFin) {
    Session s = null;
    try {
        s = HibernateUtil.getSessionFactory().openSession();
        // Requête non-générique (compatible avec les anciennes versions)
        Query q = s.createQuery(
            "SELECT SUM(m.nbrEnfant) FROM Mariage m " +
            "WHERE m.femme.id = :idf AND m.dateDebut BETWEEN :d1 AND :d2"
        );
        q.setParameter("idf", idFemme);
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

    // ✅ Femmes mariées deux fois ou plus
   public List<Femme> getFemmesMarieesDeuxFoisOuPlus() {
    Session s = null;
    try {
        s = HibernateUtil.getSessionFactory().openSession();

        // ✅ Version compatible avec Hibernate 4.x et NetBeans 8.0.2
        Query q = s.createQuery(
            "SELECT f FROM Femme f WHERE " +
            "(SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
        );

        @SuppressWarnings("unchecked")
        List<Femme> list = (List<Femme>) q.list();
        return list;

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        if (s != null) s.close();
    }
}
}
