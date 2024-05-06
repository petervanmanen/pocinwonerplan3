package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Geweldsincident.
 */
@Entity
@Table(name = "geweldsincident")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Geweldsincident implements Serializable {

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

    @Column(name = "type")
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftondergaanGeweldsincident")
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
    private Set<Werknemer> heeftondergaanWerknemers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Geweldsincident id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Geweldsincident datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Geweldsincident omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getType() {
        return this.type;
    }

    public Geweldsincident type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Werknemer> getHeeftondergaanWerknemers() {
        return this.heeftondergaanWerknemers;
    }

    public void setHeeftondergaanWerknemers(Set<Werknemer> werknemers) {
        if (this.heeftondergaanWerknemers != null) {
            this.heeftondergaanWerknemers.forEach(i -> i.setHeeftondergaanGeweldsincident(null));
        }
        if (werknemers != null) {
            werknemers.forEach(i -> i.setHeeftondergaanGeweldsincident(this));
        }
        this.heeftondergaanWerknemers = werknemers;
    }

    public Geweldsincident heeftondergaanWerknemers(Set<Werknemer> werknemers) {
        this.setHeeftondergaanWerknemers(werknemers);
        return this;
    }

    public Geweldsincident addHeeftondergaanWerknemer(Werknemer werknemer) {
        this.heeftondergaanWerknemers.add(werknemer);
        werknemer.setHeeftondergaanGeweldsincident(this);
        return this;
    }

    public Geweldsincident removeHeeftondergaanWerknemer(Werknemer werknemer) {
        this.heeftondergaanWerknemers.remove(werknemer);
        werknemer.setHeeftondergaanGeweldsincident(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Geweldsincident)) {
            return false;
        }
        return getId() != null && getId().equals(((Geweldsincident) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Geweldsincident{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
