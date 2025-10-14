package ma.projet.beans;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("F")
@NamedQueries({
    // Named query to find women married at least twice (we will use on Femme)
    @NamedQuery(name = "Femme.findAtLeastTwoMarriages",
                query = "SELECT f FROM Femme f JOIN f.mariages m GROUP BY f.id HAVING COUNT(m) >= 2")
})
@NamedNativeQueries({
    // Native query example to sum children (nombre d'enfants) between two dates for a given femme id
    @NamedNativeQuery(name = "Femme.sumChildrenBetween",
                       query = "SELECT IFNULL(SUM(m.nbrEnfant),0) FROM Mariage m WHERE m.femme_id = :femmeId AND m.dateDebut BETWEEN :start AND :end",
                       resultSetMapping = "ScalarInteger")
})
@SqlResultSetMapping(name = "ScalarInteger", columns = @ColumnResult(name = "SUM(m.nbrEnfant)"))
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages;

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }
}
