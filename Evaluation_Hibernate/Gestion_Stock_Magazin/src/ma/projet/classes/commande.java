/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.classes;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Lenovo
 */

@Entity
@Table(name = "commande")
public class commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private int id;
    
     @Temporal(TemporalType.DATE)
      private Date date;
     

    @OneToMany(mappedBy = "commande", fetch = FetchType.EAGER)
    private List<ligne_commande_produit> ligneCommandeProduits;

    public commande(Date date, List<produit> produits) {
        this.date = date;
        this.produits = produits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<produit> getProduits() {
        return produits;
    }

    public void setProduits(List<produit> produits) {
        this.produits = produits;
    }
     
     
}
