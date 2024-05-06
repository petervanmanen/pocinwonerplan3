package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Programmasoort.
 */
@Entity
@Table(name = "programmasoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Programmasoort implements Serializable {

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "voorProgrammasoorts")
    @JsonIgnoreProperties(
        value = {
            "bestaatuitActiviteits",
            "binnenprogrammaPlans",
            "heeftKostenplaats",
            "voorProgrammasoorts",
            "voorMuseumrelatie",
            "hoortbijRaadsstuks",
        },
        allowSetters = true
    )
    private Set<Programma> voorProgrammas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Programmasoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Programmasoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Programmasoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Programma> getVoorProgrammas() {
        return this.voorProgrammas;
    }

    public void setVoorProgrammas(Set<Programma> programmas) {
        if (this.voorProgrammas != null) {
            this.voorProgrammas.forEach(i -> i.removeVoorProgrammasoort(this));
        }
        if (programmas != null) {
            programmas.forEach(i -> i.addVoorProgrammasoort(this));
        }
        this.voorProgrammas = programmas;
    }

    public Programmasoort voorProgrammas(Set<Programma> programmas) {
        this.setVoorProgrammas(programmas);
        return this;
    }

    public Programmasoort addVoorProgramma(Programma programma) {
        this.voorProgrammas.add(programma);
        programma.getVoorProgrammasoorts().add(this);
        return this;
    }

    public Programmasoort removeVoorProgramma(Programma programma) {
        this.voorProgrammas.remove(programma);
        programma.getVoorProgrammasoorts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Programmasoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Programmasoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Programmasoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
