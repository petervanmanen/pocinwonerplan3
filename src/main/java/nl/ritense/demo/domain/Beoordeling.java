package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Beoordeling.
 */
@Entity
@Table(name = "beoordeling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beoordeling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "oordeel")
    private String oordeel;

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
    private Werknemer beoordeeltdoorWerknemer;

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
    private Werknemer beoordelingvanWerknemer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beoordeling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Beoordeling datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Beoordeling omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOordeel() {
        return this.oordeel;
    }

    public Beoordeling oordeel(String oordeel) {
        this.setOordeel(oordeel);
        return this;
    }

    public void setOordeel(String oordeel) {
        this.oordeel = oordeel;
    }

    public Werknemer getBeoordeeltdoorWerknemer() {
        return this.beoordeeltdoorWerknemer;
    }

    public void setBeoordeeltdoorWerknemer(Werknemer werknemer) {
        this.beoordeeltdoorWerknemer = werknemer;
    }

    public Beoordeling beoordeeltdoorWerknemer(Werknemer werknemer) {
        this.setBeoordeeltdoorWerknemer(werknemer);
        return this;
    }

    public Werknemer getBeoordelingvanWerknemer() {
        return this.beoordelingvanWerknemer;
    }

    public void setBeoordelingvanWerknemer(Werknemer werknemer) {
        this.beoordelingvanWerknemer = werknemer;
    }

    public Beoordeling beoordelingvanWerknemer(Werknemer werknemer) {
        this.setBeoordelingvanWerknemer(werknemer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beoordeling)) {
            return false;
        }
        return getId() != null && getId().equals(((Beoordeling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beoordeling{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", oordeel='" + getOordeel() + "'" +
            "}";
    }
}
