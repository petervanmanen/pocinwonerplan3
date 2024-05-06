package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sportmateriaal.
 */
@Entity
@Table(name = "sportmateriaal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sportmateriaal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftSportmateriaals")
    @JsonIgnoreProperties(
        value = { "inspectierapportDocuments", "isgevestigdinVerblijfsobject", "bedientWijk", "heeftBelijnings", "heeftSportmateriaals" },
        allowSetters = true
    )
    private Set<Binnenlocatie> heeftBinnenlocaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sportmateriaal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Sportmateriaal naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Binnenlocatie> getHeeftBinnenlocaties() {
        return this.heeftBinnenlocaties;
    }

    public void setHeeftBinnenlocaties(Set<Binnenlocatie> binnenlocaties) {
        if (this.heeftBinnenlocaties != null) {
            this.heeftBinnenlocaties.forEach(i -> i.removeHeeftSportmateriaal(this));
        }
        if (binnenlocaties != null) {
            binnenlocaties.forEach(i -> i.addHeeftSportmateriaal(this));
        }
        this.heeftBinnenlocaties = binnenlocaties;
    }

    public Sportmateriaal heeftBinnenlocaties(Set<Binnenlocatie> binnenlocaties) {
        this.setHeeftBinnenlocaties(binnenlocaties);
        return this;
    }

    public Sportmateriaal addHeeftBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.heeftBinnenlocaties.add(binnenlocatie);
        binnenlocatie.getHeeftSportmateriaals().add(this);
        return this;
    }

    public Sportmateriaal removeHeeftBinnenlocatie(Binnenlocatie binnenlocatie) {
        this.heeftBinnenlocaties.remove(binnenlocatie);
        binnenlocatie.getHeeftSportmateriaals().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sportmateriaal)) {
            return false;
        }
        return getId() != null && getId().equals(((Sportmateriaal) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sportmateriaal{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
