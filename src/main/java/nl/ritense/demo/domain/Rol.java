package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rol.
 */
@Entity
@Table(name = "rol")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftRols")
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
    private Set<Werknemer> heeftWerknemers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rol id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Rol naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Rol omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Werknemer> getHeeftWerknemers() {
        return this.heeftWerknemers;
    }

    public void setHeeftWerknemers(Set<Werknemer> werknemers) {
        if (this.heeftWerknemers != null) {
            this.heeftWerknemers.forEach(i -> i.removeHeeftRol(this));
        }
        if (werknemers != null) {
            werknemers.forEach(i -> i.addHeeftRol(this));
        }
        this.heeftWerknemers = werknemers;
    }

    public Rol heeftWerknemers(Set<Werknemer> werknemers) {
        this.setHeeftWerknemers(werknemers);
        return this;
    }

    public Rol addHeeftWerknemer(Werknemer werknemer) {
        this.heeftWerknemers.add(werknemer);
        werknemer.getHeeftRols().add(this);
        return this;
    }

    public Rol removeHeeftWerknemer(Werknemer werknemer) {
        this.heeftWerknemers.remove(werknemer);
        werknemer.getHeeftRols().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rol)) {
            return false;
        }
        return getId() != null && getId().equals(((Rol) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rol{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
