package gestion_stock_magazin;

import ma.projet.service.ProduitService;
import ma.projet.service.CategorieService;
import ma.projet.classes.produit;
import java.util.List;

public class Gestion_Stock_Magazin {

    public static void main(String[] args) {

        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();

        try {
            // 1️⃣ Afficher tous les produits
            System.out.println("=== Tous les produits ===");
            List<produit> produits = produitService.findAll();
            for (produit p : produits) {
                System.out.println(p.getReference() + " | " + p.getNom() + " | " + p.getPrix() + " DH");
            }

            // 2️⃣ Afficher les produits par catégorie (ex: id = 1)
            System.out.println("\n=== Produits de la catégorie 1 ===");
            List<produit> produitsCat = produitService.findByCategorie(1);
            for (produit p : produitsCat) {
                System.out.println(p.getReference() + " | " + p.getNom() + " | " + p.getPrix() + " DH");
            }

            // 3️⃣ Afficher les produits d’une commande donnée (ex: commande id = 4)
            System.out.println("\n=== Produits de la commande 4 ===");
            List<ligne_commande_produit> ligne_commande_produit = produitService.findProductsByCommande(4);
            for (ligneCommande lc : ligne_commande_produit) {
                System.out.println(lc.getProduit().getReference() + " | " +
                        lc.getProduit().getPrix(
