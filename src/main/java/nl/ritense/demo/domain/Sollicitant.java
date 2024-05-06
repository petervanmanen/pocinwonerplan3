package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sollicitant.
 */
@Entity
@Table(name = "sollicitant")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sollicitant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solliciteertopfunctieSollicitant")
    @JsonIgnoreProperties(
        value = { "opvacatureVacature", "solliciteertopfunctieSollicitant", "solliciteertWerknemer", "inkadervanSollicitatiegespreks" },
        allowSetters = true
    )
    private Set<Sollicitatie> solliciteertopfunctieSollicitaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "kandidaatSollicitants")
    @JsonIgnoreProperties(
        value = { "inkadervanSollicitatie", "kandidaatSollicitants", "doetsollicitatiegesprekWerknemers" },
        allowSetters = true
    )
    private Set<Sollicitatiegesprek> kandidaatSollicitatiegespreks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sollicitant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Sollicitatie> getSolliciteertopfunctieSollicitaties() {
        return this.solliciteertopfunctieSollicitaties;
    }

    public void setSolliciteertopfunctieSollicitaties(Set<Sollicitatie> sollicitaties) {
        if (this.solliciteertopfunctieSollicitaties != null) {
            this.solliciteertopfunctieSollicitaties.forEach(i -> i.setSolliciteertopfunctieSollicitant(null));
        }
        if (sollicitaties != null) {
            sollicitaties.forEach(i -> i.setSolliciteertopfunctieSollicitant(this));
        }
        this.solliciteertopfunctieSollicitaties = sollicitaties;
    }

    public Sollicitant solliciteertopfunctieSollicitaties(Set<Sollicitatie> sollicitaties) {
        this.setSolliciteertopfunctieSollicitaties(sollicitaties);
        return this;
    }

    public Sollicitant addSolliciteertopfunctieSollicitatie(Sollicitatie sollicitatie) {
        this.solliciteertopfunctieSollicitaties.add(sollicitatie);
        sollicitatie.setSolliciteertopfunctieSollicitant(this);
        return this;
    }

    public Sollicitant removeSolliciteertopfunctieSollicitatie(Sollicitatie sollicitatie) {
        this.solliciteertopfunctieSollicitaties.remove(sollicitatie);
        sollicitatie.setSolliciteertopfunctieSollicitant(null);
        return this;
    }

    public Set<Sollicitatiegesprek> getKandidaatSollicitatiegespreks() {
        return this.kandidaatSollicitatiegespreks;
    }

    public void setKandidaatSollicitatiegespreks(Set<Sollicitatiegesprek> sollicitatiegespreks) {
        if (this.kandidaatSollicitatiegespreks != null) {
            this.kandidaatSollicitatiegespreks.forEach(i -> i.removeKandidaatSollicitant(this));
        }
        if (sollicitatiegespreks != null) {
            sollicitatiegespreks.forEach(i -> i.addKandidaatSollicitant(this));
        }
        this.kandidaatSollicitatiegespreks = sollicitatiegespreks;
    }

    public Sollicitant kandidaatSollicitatiegespreks(Set<Sollicitatiegesprek> sollicitatiegespreks) {
        this.setKandidaatSollicitatiegespreks(sollicitatiegespreks);
        return this;
    }

    public Sollicitant addKandidaatSollicitatiegesprek(Sollicitatiegesprek sollicitatiegesprek) {
        this.kandidaatSollicitatiegespreks.add(sollicitatiegesprek);
        sollicitatiegesprek.getKandidaatSollicitants().add(this);
        return this;
    }

    public Sollicitant removeKandidaatSollicitatiegesprek(Sollicitatiegesprek sollicitatiegesprek) {
        this.kandidaatSollicitatiegespreks.remove(sollicitatiegesprek);
        sollicitatiegesprek.getKandidaatSollicitants().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sollicitant)) {
            return false;
        }
        return getId() != null && getId().equals(((Sollicitant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sollicitant{" +
            "id=" + getId() +
            "}";
    }
}
