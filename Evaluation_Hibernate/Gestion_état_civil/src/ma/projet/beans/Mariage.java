package ma.projet.beans;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Mariage")
@NamedQueries({
    @NamedQuery(name = "Mariage.findByHommeBetweenDates",
                query = "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateDebut BETWEEN :start AND :end"),
    @NamedQuery(name = "Mariage.sumChildrenForFemmeBetween",
                query = "SELECT COALESCE(SUM(m.nbrEnfant),0) FROM Mariage m WHERE m.femme.id = :femmeId AND m.dateDebut BETWEEN :start AND :end")
})
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nbrEnfant;

    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;

    // Getters / Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int nbrEnfant) { this.nbrEnfant = nbrEnfant; }
    public Homme getHomme() { return homme; }
    public void setHomme(Homme homme) { this.homme = homme; }
    public Femme getFemme() { return femme; }
    public void setFemme(Femme femme) { this.femme = femme; }
}
