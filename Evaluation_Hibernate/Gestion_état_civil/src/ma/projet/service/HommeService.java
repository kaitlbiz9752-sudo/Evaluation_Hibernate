package ma.projet.service;

import java.time.LocalDate;
import java.util.List;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

public class HommeService implements IDao<Homme> {

    @Override
    public Homme save(Homme f) {
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
    public Homme update(Homme f) {
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
            Homme f = (Homme) s.get(Homme.class, (Integer) id);
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
    public Homme findById(Object id) {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            return (Homme) s.get(Homme.class, (Integer) id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (s != null) s.close();
        }
    }

    @Override
    public List<Homme> findAll() {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createQuery("from Homme");        
            @SuppressWarnings("unchecked")
            List<Homme> list = (List<Homme>) q.list();    
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (s != null) s.close();
        }
    }

    // Nombre d’enfants entre deux dates
    public int getNombreEnfantsBetween(Integer idHomme, LocalDate dateDebut, LocalDate dateFin) {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createQuery(
                "SELECT SUM(m.nbrEnfant) FROM Mariage m " +
                "WHERE m.homme.id = :idHomme AND m.dateDebut BETWEEN :d1 AND :d2"
            );
            q.setParameter("idHomme", idHomme);
            q.setParameter("d1", dateDebut);
            q.setParameter("d2", dateFin);

            Object res = q.uniqueResult(); 
            if (res == null) return 0;
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

    // Hommes mariés deux fois ou plus
    public List<Homme> getHommesMarieesDeuxFoisOuPlus() {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createQuery(
                "SELECT h FROM Homme h WHERE " +
                "(SELECT COUNT(m) FROM Mariage m WHERE m.homme = h) >= 2"
            );
            @SuppressWarnings("unchecked")
            List<Homme> list = (List<Homme>) q.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (s != null) s.close();
        }
    }

    public List<Mariage> findEpousesBetween(Integer id, LocalDate d1, LocalDate d2) {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createQuery(
                "FROM Mariage m WHERE m.homme.id = :id AND m.dateDebut BETWEEN :d1 AND :d2"
            );
            q.setParameter("id", id);
            q.setParameter("d1", d1);
            q.setParameter("d2", d2);
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

    // ✅ Hommes mariés à 4 femmes entre deux dates
    public List<Homme> hommesMariesAvecQuatreFemmes(LocalDate debut, LocalDate fin) {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createQuery(
    "SELECT m.homme FROM Mariage m " +
    "WHERE m.dateDebut BETWEEN :debut AND :fin " +
    "GROUP BY m.homme " +
    "HAVING COUNT(m.femme) = 4"
);
q.setParameter("debut", debut);
q.setParameter("fin", fin);

@SuppressWarnings("unchecked")
List<Homme> list = (List<Homme>) q.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (s != null) s.close();
        }
    }

    public List<Mariage> findMariagesForHomme(Integer id) {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createQuery(
                "FROM Mariage m WHERE m.homme.id = :id"
            );
            q.setParameter("id", id);
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
