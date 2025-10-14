/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 *
 * @author Lenovo
 */
public class HibernateUtil {
     private static final SessionFactory sessionFactory;

    static {
        try {
            // Lecture du fichier de configuration hibernate.cfg.xml
            sessionFactory = new Configuration().configure("config/hibernate.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Erreur de création de la SessionFactory : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
     public static void shutdown() {
        getSessionFactory().close();
    }
}
