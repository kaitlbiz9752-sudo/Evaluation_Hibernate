package ma.projet;

import ma.projet.dao.EmployeService;
import ma.projet.dao.ProjetService;
import ma.projet.dao.TacheService;
import ma.projet.dao.EmployeTacheService;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import java.util.Date;

public class Gestion_Projets {

    public static void main(String[] args) {
        // 1. Initialiser les services
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        // 2. Créer et enregistrer quelques employés
        Employe e1 = new Employe(1, "Ahmed", "Berrada", "ahmed@gmail.com");
        Employe e2 = new Employe(1, "Mohamed", "Ahmad", "Mohamed@gmail.com");
        employeService.create(e1);
        employeService.create(e2);

        // 3. Créer et enregistrer un projet
        Projet p1 = new Projet("Gestion de stock", new Date(), new Date());
        projetService.create(p1);

        // 4. Créer et enregistrer des tâches
        Tache t1 = new Tache("Analyse", new Date(), new Date(), 1500);
        Tache t2 = new Tache("Conception", new Date(), new Date(), 1800);
        Tache t3 = new Tache("Développement", new Date(), new Date(), 2500);
        tacheService.create(t1);
        tacheService.create(t2);
        tacheService.create(t3);

        // 5. Affecter des tâches aux employés avec dates réelles
        EmployeTache et1 = new EmployeTache(e1, t1, new Date(), new Date());
        EmployeTache et2 = new EmployeTache(e1, t2, new Date(), new Date());
        EmployeTache et3 = new EmployeTache(e2, t3, new Date(), new Date());
        employeTacheService.create(et1);
        employeTacheService.create(et2);
        employeTacheService.create(et3);

        // 6. Tester les méthodes demandées

        System.out.println("------ Tâches réalisées par l'employé e1 ------");
        employeService.getTachesByEmploye(e1.getId());

        System.out.println("------ Projets gérés par l'employé e1 ------");
        employeService.getProjetsByEmploye(e1.getId());

        System.out.println("------ Tâches planifiées pour le projet p1 ------");
        projetService.getTachesPlanifiees(p1.getId());

        System.out.println("------ Tâches réalisées avec les dates réelles ------");
        projetService.getTachesRealisees(p1.getId());

        System.out.println("------ Tâches dont le prix > 1000 DH ------");
        tacheService.getTachesPrixSup1000();

        System.out.println("------ Tâches réalisées entre deux dates ------");
        tacheService.getTachesEntreDates(new Date(), new Date());

        System.out.println("✅ Test terminé avec succès !");
    }
}
