package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sollicitatie.
 */
@Entity
@Table(name = "sollicitatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sollicitatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vacaturebijfunctieFunctie", "opvacatureSollicitaties" }, allowSetters = true)
    private Vacature opvacatureVacature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "solliciteertopfunctieSollicitaties", "kandidaatSollicitatiegespreks" }, allowSetters = true)
    private Sollicitant solliciteertopfunctieSollicitant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "beoordeeltdoorBeoordelings",
            "beoordelingvanBeoordelings",
            "dientinDeclaraties",
            "medewerkerheeftdienstverbandDienstverbands",
            "solliciteertSollicitaties",
            "heeftverlofVerlofs",
            "heeftverzuimVerzuims",
            "heeftondergaanGeweldsincident",
            "heeftRols",
            "doetsollicitatiegesprekSollicitatiegespreks",
        },
        allowSetters = true
    )
    private Werknemer solliciteertWerknemer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inkadervanSollicitatie")
    @JsonIgnoreProperties(
        value = { "inkadervanSollicitatie", "kandidaatSollicitants", "doetsollicitatiegesprekWerknemers" },
        allowSetters = true
    )
    private Set<Sollicitatiegesprek> inkadervanSollicitatiegespreks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sollicitatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Sollicitatie datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Vacature getOpvacatureVacature() {
        return this.opvacatureVacature;
    }

    public void setOpvacatureVacature(Vacature vacature) {
        this.opvacatureVacature = vacature;
    }

    public Sollicitatie opvacatureVacature(Vacature vacature) {
        this.setOpvacatureVacature(vacature);
        return this;
    }

    public Sollicitant getSolliciteertopfunctieSollicitant() {
        return this.solliciteertopfunctieSollicitant;
    }

    public void setSolliciteertopfunctieSollicitant(Sollicitant sollicitant) {
        this.solliciteertopfunctieSollicitant = sollicitant;
    }

    public Sollicitatie solliciteertopfunctieSollicitant(Sollicitant sollicitant) {
        this.setSolliciteertopfunctieSollicitant(sollicitant);
        return this;
    }

    public Werknemer getSolliciteertWerknemer() {
        return this.solliciteertWerknemer;
    }

    public void setSolliciteertWerknemer(Werknemer werknemer) {
        this.solliciteertWerknemer = werknemer;
    }

    public Sollicitatie solliciteertWerknemer(Werknemer werknemer) {
        this.setSolliciteertWerknemer(werknemer);
        return this;
    }

    public Set<Sollicitatiegesprek> getInkadervanSollicitatiegespreks() {
        return this.inkadervanSollicitatiegespreks;
    }

    public void setInkadervanSollicitatiegespreks(Set<Sollicitatiegesprek> sollicitatiegespreks) {
        if (this.inkadervanSollicitatiegespreks != null) {
            this.inkadervanSollicitatiegespreks.forEach(i -> i.setInkadervanSollicitatie(null));
        }
        if (sollicitatiegespreks != null) {
            sollicitatiegespreks.forEach(i -> i.setInkadervanSollicitatie(this));
        }
        this.inkadervanSollicitatiegespreks = sollicitatiegespreks;
    }

    public Sollicitatie inkadervanSollicitatiegespreks(Set<Sollicitatiegesprek> sollicitatiegespreks) {
        this.setInkadervanSollicitatiegespreks(sollicitatiegespreks);
        return this;
    }

    public Sollicitatie addInkadervanSollicitatiegesprek(Sollicitatiegesprek sollicitatiegesprek) {
        this.inkadervanSollicitatiegespreks.add(sollicitatiegesprek);
        sollicitatiegesprek.setInkadervanSollicitatie(this);
        return this;
    }

    public Sollicitatie removeInkadervanSollicitatiegesprek(Sollicitatiegesprek sollicitatiegesprek) {
        this.inkadervanSollicitatiegespreks.remove(sollicitatiegesprek);
        sollicitatiegesprek.setInkadervanSollicitatie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sollicitatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Sollicitatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sollicitatie{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
