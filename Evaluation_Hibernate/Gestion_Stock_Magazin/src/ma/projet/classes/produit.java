/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.classes;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 *
 * @author Lenovo
 */
@NamedQuery(name="produit.findPrixSup100", query="from produit p where p.prix > 100")
@Entity
@Table(name = "produit")
public class produit {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private categorie categorie;

    @OneToMany(mappedBy = "produit", fetch = FetchType.EAGER)
    private List<ligne_commande_produit> ligneCommandeProduits;

    public produit(String reference, float prix, categorie categorie, List<ligne_commande_produit> ligneCommandeProduits) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
        this.ligneCommandeProduits = ligneCommandeProduits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(categorie categorie) {
        this.categorie = categorie;
    }

    public List<ligne_commande_produit> getLigneCommandeProduits() {
        return ligneCommandeProduits;
    }

    public void setLigneCommandeProduits(List<ligne_commande_produit> ligneCommandeProduits) {
        this.ligneCommandeProduits = ligneCommandeProduits;
    }
    
    
}
