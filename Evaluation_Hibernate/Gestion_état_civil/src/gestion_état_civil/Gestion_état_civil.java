package gestion_état_civil;

import ma.projet.beans.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Gestion_état_civil {
    public static void main(String[] args) {
        HommeService hommeService = new HommeService();
        FemmeService femmeService = new FemmeService();
        MariageService mariageService = new MariageService();

        // Création de 5 hommes
        List<Homme> hommes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Homme h = new Homme();
            h.setNom("HOMME_NOM_" + i);
            h.setPrenom("PrenomH" + i);
            h.setDateNaissance(LocalDate.of(1970 + i, 1 + (i % 11), 10));
            hommes.add(hommeService.save(h));
        }

        // Création de 10 femmes
        List<Femme> femmes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Femme f = new Femme();
            f.setNom("FEMME_NOM_" + i);
            f.setPrenom("PrenomF" + i);
            f.setDateNaissance(LocalDate.of(1965 + i, 1 + (i % 12), 5));
            femmes.add(femmeService.save(f));
        }

        // Mariages de test pour Homme 1
        Homme h1 = hommes.get(0);
        for (int i = 0; i < 4; i++) {
            Mariage m = new Mariage();
            m.setHomme(h1);
            m.setFemme(femmes.get(i));
            m.setDateDebut(LocalDate.of(1990 + i, 1 + i, 3));
            m.setDateFin(null); // mariage en cours
            m.setNbrEnfant(1 + i);
            mariageService.save(m);
        }

        // Homme 2 marié 2 fois
        Homme h2 = hommes.get(1);
        for (int i = 4; i < 6; i++) {
            Mariage m = new Mariage();
            m.setHomme(h2);
            m.setFemme(femmes.get(i));
            m.setDateDebut(LocalDate.of(1988 + i, 3, 10));
            if (i == 4) m.setDateFin(LocalDate.of(1990, 3, 9)); // mariage fini
            m.setNbrEnfant(i % 3);
            mariageService.save(m);
        }

        // Autre mariage aléatoire
        Mariage m = new Mariage();
        m.setHomme(hommes.get(2));
        m.setFemme(femmes.get(6));
        m.setDateDebut(LocalDate.of(2000, 11, 4));
        m.setNbrEnfant(3);
        mariageService.save(m);

        // ---------------- AFFICHAGES ----------------

        System.out.println("----- Liste des femmes -----");
        femmeService.findAll().forEach(f ->
            System.out.println(f.getNom() + " " + f.getPrenom() + " - née le " + f.getDateNaissance())
        );

        System.out.println("\n----- Femme la plus âgée -----");
        Femme oldest = femmeService.findAll().stream()
                .min((f1, f2) -> f1.getDateNaissance().compareTo(f2.getDateNaissance()))
                .orElse(null);
        if (oldest != null)
            System.out.println(oldest.getNom() + " " + oldest.getPrenom() + " - " + oldest.getDateNaissance());

        System.out.println("\n----- Épouses de l’homme 1 entre 1989 et 1995 -----");
        List<Mariage> epouses = hommeService.findEpousesBetween(
                h1.getId(),
                LocalDate.of(1989, 1, 1),
                LocalDate.of(1995, 12, 31)
        );
        int i = 1;
        for (Mariage mm : epouses) {
            System.out.println(i++ + ". Femme : " + mm.getFemme().getNom()
                    + " | Date Début : " + mm.getDateDebut()
                    + " | Nbr Enfants : " + mm.getNbrEnfant());
        }

        System.out.println("\n----- Nombre d’enfants de la femme 1 entre 1985 et 2000 -----");
        int enfants = femmeService.getNombreEnfantsBetween(
                femmes.get(0).getId(),
                LocalDate.of(1985, 1, 1),
                LocalDate.of(2000, 12, 31)
        );
        System.out.println("Nombre enfants = " + enfants);

        System.out.println("\n----- Femmes mariées deux fois ou plus -----");
        femmeService.getFemmesMarieesDeuxFoisOuPlus()
                .forEach(f -> System.out.println(f.getNom()));

        System.out.println("\n----- Hommes mariés à 4 femmes entre 1989 et 2005 -----");
        hommeService.hommesMariesAvecQuatreFemmes(
                LocalDate.of(1989, 1, 1),
                LocalDate.of(2005, 12, 31)
        ).forEach(Homme -> System.out.println(Homme.getNom())); // <- corrigé

        System.out.println("\n----- Mariages d’un homme donné (Homme 1) avec tous les détails -----");
        List<Mariage> allMariages = hommeService.findMariagesForHomme(h1.getId());

        System.out.println("Nom : " + h1.getNom() + " " + h1.getPrenom());
        System.out.println("Mariages en cours :");
        i = 1;
        for (Mariage mm : allMariages) {
            if (mm.getDateFin() == null) {
                System.out.println(i++ + ". Femme : " + mm.getFemme().getNom()
                        + " | Date Début : " + mm.getDateDebut()
                        + " | Nbr Enfants : " + mm.getNbrEnfant());
            }
        }

        System.out.println("Mariages échoués :");
        i = 1;
        for (Mariage mm : allMariages) {
            if (mm.getDateFin() != null) {
                System.out.println(i++ + ". Femme : " + mm.getFemme().getNom()
                        + " | Date Début : " + mm.getDateDebut()
                        + " | Date Fin : " + mm.getDateFin()
                        + " | Nbr Enfants : " + mm.getNbrEnfant());
            }
        }

        // Fermer Hibernate proprement
        HibernateUtil.shutdown();
    }
}
